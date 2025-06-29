package Controller;

import Model.Usuario.Aluno;
import Model.Usuario.Treinador;
import View.TelaPrincipalTreinador;
import View.TelaPrincipalAluno;
import javax.swing.*;
import Data.DataManager;
import java.io.IOException;
import java.util.ArrayList;
import Exception.ValidacaoException;

public class TreinadorController {

    private TelaPrincipalTreinador view;
    private Treinador treinador;

    public TreinadorController(TelaPrincipalTreinador view, String usernameTreinador) {
        this.view = view;
        this.treinador = new Treinador(usernameTreinador, null, 0);
    }

    public void carregarDadosIniciais() {
        try {
            Treinador treinadorCarregado = DataManager.carregarTreinador(this.treinador.getUsername());
            
            //Se o treinador não foi encontrado, cria um novo treinador
            if (treinadorCarregado == null) { 
                this.treinador = criarNovoTreinador(treinador.getUsername());
                
                if (this.treinador != null) {
                    DataManager.salvarTreinador(this.treinador);
                    view.mostrarMensagem("Novo treinador criado com sucesso!", "Cadastro de novo usuário",
                            JOptionPane.INFORMATION_MESSAGE);
                } 
                else {
                    view.dispose();
                    new View.TelaSelecaoUsuario().setVisible(true);
                    return;
                }
            //Se já existe treinador, salva os dados em "treinador"
            } else {
                this.treinador = treinadorCarregado;
            }

            // Carrega a lista de alunos do treinador na view
            carregarAlunos();

        } catch (java.io.IOException | ValidacaoException e) {
            view.mostrarMensagem("Erro ao carregar dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            view.dispose();
            new View.TelaSelecaoUsuario().setVisible(true);
        }
    }

    public void salvarDados() {
        try {
            DataManager.salvarTreinador(treinador);
            view.mostrarMensagem("Dados salvos com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } 
        catch (IOException e) {
            view.mostrarMensagem("Erro ao salvar dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Treinador criarNovoTreinador(String username) {
        try {
            //Insere o nome do treinador
            String nome = JOptionPane.showInputDialog(view,
                    "Bem-vindo, novo treinador!\nDigite seu nome completo:",
                    "Cadastro de Treinador - Nome",
                    JOptionPane.QUESTION_MESSAGE);

            //Caso o usuário não tenha inserido o nome, retorna null
            if (nome == null) {
                return null;
            }
            
            if (nome.trim().isEmpty()) {
                throw new ValidacaoException("O nome não pode ser vazio.");
            }
            if (!nome.matches("[a-zA-Z ]+")) {
                throw new ValidacaoException("O nome deve conter apenas letras e espaços.");
            }

            //Insere Idade do treinador
            String idadeStr = JOptionPane.showInputDialog(view,
                    "Digite sua idade:",
                    "Cadastro de Treinador - Idade",
                    JOptionPane.QUESTION_MESSAGE);
            
            //Caso o usuário nao tenha inserido a idade, retorna null
            if (idadeStr == null) { 
                return null;
            }
            if (idadeStr.trim().isEmpty()) {
                throw new ValidacaoException("A idade não pode ser vazia.");
            }
            int idade = Integer.parseInt(idadeStr.trim());
            if (idade < 18 || idade > 100) {
                throw new ValidacaoException("A idade deve ser um número entre 18 e 100.");
            }

            //Se tudo estiver ok, cria e retorna o novo treinador
            return new Treinador(username, nome, idade);

        } catch (NumberFormatException e) {
            view.mostrarMensagem("Por favor, digite uma idade válida.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            return criarNovoTreinador(username); // Tenta novamente
        } catch (ValidacaoException e) {
            view.mostrarMensagem(e.getMessage(), "Dados Inválidos", JOptionPane.ERROR_MESSAGE);
            return criarNovoTreinador(username); // Tenta novamente
        }
    }

    public void carregarAlunos() {
        ArrayList<Aluno> alunosCarregados = new ArrayList<>();
        
        if (treinador.getAlunos() != null) {
            
            for (Aluno alunoInfo : treinador.getAlunos()) {
                try {
                    Aluno alunoCompleto = DataManager.carregarAluno(alunoInfo.getUsername());
                    if (alunoCompleto != null) {
                        alunosCarregados.add(alunoCompleto);
                    }
                } 
                catch (IOException | ValidacaoException e) {
                    System.err.println("Não foi possível carregar o aluno: " + alunoInfo.getUsername());
                }
            }
        }
        view.atualizarListaAlunos(alunosCarregados);
    }

    //Adiciona um aluno existente à lista do treinador
    public void adicionarAluno() {
        String username = JOptionPane.showInputDialog(view, "Digite o nome de usuário do aluno (já cadastrado):",
                "Adicionar Aluno", JOptionPane.PLAIN_MESSAGE);
        
        if (username != null && !username.trim().isEmpty()) {
            Aluno aluno = Aluno.buscarAlunoPorUsername(username.trim());
            
            if (aluno != null) {
                //Verifica se o aluno já está na lista do treinador
                boolean jaExiste = treinador.getAlunos().stream()
                        .anyMatch(a -> a.getUsername().equals(aluno.getUsername()));
                
                if (!jaExiste) {
                    treinador.addAluno(aluno);
                    view.atualizarListaAlunos(treinador.getAlunos());
                    view.mostrarMensagem("Aluno adicionado à sua lista!\nClique em 'Salvar Alterações' para persistir.",
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                }

                else {
                    view.mostrarMensagem("Esse aluno já está na sua lista.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            } 
            else {
                view.mostrarMensagem("Aluno não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //Remove um aluno da lista do treinador
    public void removerAluno(Aluno aluno) {
        if (aluno == null) {
            view.mostrarMensagem("Selecione um aluno para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(view,
                "Remover '" + aluno.getUsername() + "' da sua lista? (O aluno não será excluído do sistema)",
                "Confirmação",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            treinador.removeAluno(aluno);
            //Atualiza a view diretamente com a lista atual do treinador
            view.atualizarListaAlunos(treinador.getAlunos());
            view.mostrarMensagem("Aluno removido da sua lista.\nClique em 'Salvar Alterações' para persistir.",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    //Permite editar os treinos do aluno selecionado
    public void editarTreinosAluno(Aluno aluno) {
        if (aluno == null) {
            view.mostrarMensagem("Selecione um aluno para editar os treinos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        //Abre a tela do aluno, permitindo ao treinador editar os treinos
        System.setProperty("treinador.username", this.treinador.getUsername());
        TelaPrincipalAluno telaAluno = new TelaPrincipalAluno(aluno.getUsername(), true);
        telaAluno.setVisible(true);
    }

    public void sair() {
        String[] opcoes = { "Salvar e Sair", "Sair sem Salvar", "Cancelar" };
        int escolha = JOptionPane.showOptionDialog(view, "Deseja salvar as alterações antes de sair?",
                "Sair",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]);

        if (escolha == 0) //Salvar e sair
            salvarDados();
        else if (escolha == 2 || escolha == JOptionPane.CLOSED_OPTION){
            return; //Retorna pra tela inicial
        }
        view.dispose();
        //Volta para a tela de seleção de usuário
        new View.TelaSelecaoUsuario().setVisible(true);
    }
}
