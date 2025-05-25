package tad.listasEncadeadas;

/**
 * Implementação de uma lista simplesmente encadeada genérica.
 * Utiliza nós sentinela para cabeça (head) e cauda (tail) para simplificar as operações de borda.
 * Os elementos são inseridos no final da lista por padrão, ou em um índice específico.
 *
 * @param <T> o tipo de dado armazenado na lista, deve ser comparável.
 */
public class ListaEncadeadaImpl<T extends Comparable<T>> implements ListaEncadeadaIF<T>{
	
//	NodoListaEncadeada<T> cabeca = null;
	
	NodoListaEncadeada<T> cabeca = null; // Estratégia usando marcação sentinela
	NodoListaEncadeada<T> cauda = null;// Estratégia usando marcação sentinela
	private int tamanhoAtual = 0; // Adicionado
	
	/**
	 * Constrói uma lista simplesmente encadeada vazia, inicializando os nós sentinela.
	 */
	public ListaEncadeadaImpl() {// Estratégia usando marcação sentinela
		cabeca = new NodoListaEncadeada<T>();
		cauda = new NodoListaEncadeada<T>();
		cabeca.setProximo(cauda);
		tamanhoAtual = 0; // Inicializa o tamanho
	}
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEmpty() {
		return tamanhoAtual == 0;
		// Alternativamente: return cabeca.getProximo() == cauda;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int size() {
		return tamanhoAtual;
	}

	/**
	 * {@inheritDoc}
	 * Retorna o nó que contém a chave, ou {@code null} se não for encontrado.
	 */
	@Override
	public NodoListaEncadeada<T> search(T chave) {
		NodoListaEncadeada<T> atual = cabeca.getProximo();
		while (atual != cauda) {
			if (atual.getChave() != null && atual.getChave().equals(chave)) {
				return atual; // Retorna o nodo quando encontrar a chave
			}
			atual = atual.getProximo();
		}
		return null; // Retorna null quando não encontrar a chave
	}

	/**
	 * {@inheritDoc}
	 * Insere o elemento no final da lista.
	 */
	@Override
	public void insert(T chave) {
		NodoListaEncadeada<T> novoNo = new NodoListaEncadeada<>(chave);
		NodoListaEncadeada<T> penultimo = cabeca;
		while (penultimo.getProximo() != cauda) {
			penultimo = penultimo.getProximo();
		}
		novoNo.setProximo(cauda);
		penultimo.setProximo(novoNo);
		tamanhoAtual++;
	}

	/**
	 * {@inheritDoc}
	 * Remove a primeira ocorrência do elemento com a chave especificada.
	 * @return o nó removido, ou {@code null} se a chave não for encontrada.
	 * @throws ListaVaziaException se a lista estiver vazia antes da tentativa de remoção.
	 */
	@Override
	public NodoListaEncadeada<T> remove(T chave) {
		if (isEmpty()) {
			throw new ListaVaziaException();
		}

		NodoListaEncadeada<T> anterior = cabeca;
		NodoListaEncadeada<T> atual = cabeca.getProximo();
		NodoListaEncadeada<T> removido = null;

		while (atual != cauda) {
			if (atual.getChave() != null && atual.getChave().equals(chave)) {
				removido = new NodoListaEncadeada<>(atual.getChave());
				anterior.setProximo(atual.getProximo());
				atual.setProximo(null);
				tamanhoAtual--;
				return removido;
			}
			anterior = atual;
			atual = atual.getProximo();
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @return um array contendo todos os elementos da lista na ordem de inserção,
	 *         ou {@code null} se a lista estiver vazia.
	 */
	@Override
	public T[] toArray(Class<T> clazz) {
		if (isEmpty()) {
			return null;
		}

		@SuppressWarnings("unchecked")
		T[] array = (T[]) java.lang.reflect.Array.newInstance(clazz, tamanhoAtual);
		
		NodoListaEncadeada<T> atual = cabeca.getProximo();
		int i = 0;
		while (atual != cauda && i < tamanhoAtual) {
			array[i] = atual.getChave();
			i++;
			atual = atual.getProximo();
		}
		
		return array;
	}

	/**
	 * {@inheritDoc}
	 * Retorna uma representação em String dos elementos na ordem de inserção, separados por ", ".
	 */
	@Override
	public String imprimeEmOrdem() {
		StringBuilder sb = new StringBuilder();
		NodoListaEncadeada<T> atual = cabeca.getProximo();
		while (atual != cauda) {
			sb.append(atual.getChave());
			atual = atual.getProximo();
			if (atual != cauda) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 * Retorna uma representação em String dos elementos na ordem inversa de inserção, separados por ", ".
	 */
	@Override
	public String imprimeInverso() {
		StringBuilder sb = new StringBuilder();
		imprimeInversoRecursivo(cabeca.getProximo(), sb);
		// Remove a vírgula e o espaço extras do final, se houver
		if (sb.length() > 2 && sb.substring(sb.length() - 2).equals(", ")) {
			sb.setLength(sb.length() - 2);
		}
		return sb.toString();
	}

	/**
	 * Método auxiliar recursivo para construir a string inversa.
	 * @param no o nó atual na recursão.
	 * @param sb o StringBuilder para construir a string.
	 */
	private void imprimeInversoRecursivo(NodoListaEncadeada<T> no, StringBuilder sb) {
		if (no == cauda) {
			return;
		}
		imprimeInversoRecursivo(no.getProximo(), sb);
		sb.append(no.getChave());
		// Adiciona separador apenas se não for o primeiro elemento a ser adicionado (na ordem inversa)
		// ou seja, se o StringBuilder já tiver conteúdo antes deste append.
		// No entanto, a lógica de remover no final é mais simples de acertar.
		// Melhor adicionar sempre e remover no final se necessário.
		sb.append(", "); 
	}

	/**
	 * {@inheritDoc}
	 * Retorna o nó sucessor ao nó que contém a chave especificada.
	 * @return o nó sucessor, ou {@code null} se a chave não for encontrada ou não tiver sucessor.
	 */
	@Override
	public NodoListaEncadeada<T> sucessor(T chave) {
		NodoListaEncadeada<T> atual = cabeca.getProximo();
		NodoListaEncadeada<T> sucessor = null;
		
		while (atual != cauda) {
			if (atual.getChave() != null && atual.getChave().equals(chave)) {
				sucessor = atual.getProximo();
				break;
			}
			atual = atual.getProximo();
		}
		
		return sucessor != cauda ? sucessor : null;
	}

	/**
	 * {@inheritDoc}
	 * Retorna o nó predecessor ao nó que contém a chave especificada.
	 * @return o nó predecessor (que contém dados), ou {@code null} se a chave não for encontrada,
	 *         for o primeiro elemento, ou não tiver predecessor de dados.
	 */
	@Override
	public NodoListaEncadeada<T> predecessor(T chave) {
		NodoListaEncadeada<T> atual = cabeca.getProximo();
		NodoListaEncadeada<T> anterior = cabeca; // Começa com o sentinela cabeça como 'anterior' potencial

		// Caso especial: se a chave buscada for o primeiro elemento, seu predecessor é null (ou o sentinela cabeça, dependendo da definição)
		// Os testes parecem esperar null se não houver predecessor de dados.
		if (atual != cauda && atual.getChave() != null && atual.getChave().equals(chave)) {
			return null; // O primeiro elemento não tem predecessor de dados
		}

		while (atual != cauda) {
			if (atual.getChave() != null && atual.getChave().equals(chave)) {
				// Se 'anterior' ainda é o sentinela 'cabeca', significa que 'atual' é o primeiro nó com dados.
				// No entanto, o caso acima já tratou o primeiro elemento.
				// Aqui, 'anterior' deve ser um nó de dados válido.
				return anterior;
			}
			anterior = atual;
			atual = atual.getProximo();
		}
		return null; // Chave não encontrada, ou não tem predecessor (ex: é o primeiro, já tratado)
	}

	/**
	 * {@inheritDoc}
	 * Insere um elemento na posição especificada (índice baseado em zero).
	 * Se o índice for igual ao tamanho atual da lista, o elemento é inserido no final.
	 * @param chave o elemento a ser inserido.
	 * @param index a posição onde o elemento deve ser inserido.
	 * @throws IndexOutOfBoundsException se o índice for inválido (menor que 0 ou maior que o tamanho atual da lista).
	 */
	@Override
	public void insert(T chave, int index) {
		if (index < 0 || index > tamanhoAtual) {
			throw new IndexOutOfBoundsException("Índice inválido: " + index);
		}

		NodoListaEncadeada<T> novoNo = new NodoListaEncadeada<>(chave);
		
		if (index == tamanhoAtual) {
			// Inserir no final
			NodoListaEncadeada<T> penultimo = cabeca;
			while (penultimo.getProximo() != cauda) {
				penultimo = penultimo.getProximo();
			}
			novoNo.setProximo(cauda);
			penultimo.setProximo(novoNo);
		} else {
			// Inserir na posição específica
			NodoListaEncadeada<T> anterior = cabeca;
			for (int i = 0; i < index; i++) {
				anterior = anterior.getProximo();
			}
			novoNo.setProximo(anterior.getProximo());
			anterior.setProximo(novoNo);
		}
		
		tamanhoAtual++;
	}

}
