package tad.listasEncadeadas;

/**
 * Implementação de uma lista duplamente encadeada genérica.
 * Utiliza nós sentinela para cabeça (head) e cauda (tail) para simplificar as operações de borda.
 * Permite inserção no início e no fim, remoção de elementos específicos, remoção do primeiro e último,
 * busca, e outras operações de lista.
 *
 * @param <T> o tipo de dado armazenado na lista, deve ser comparável.
 */
public class ListaDuplamenteEncadeadaImpl<T extends Comparable<T>> implements ListaDuplamenteEncadeadaIF<T> {
	
	//TODO: implementar o nó cabeça
	//TODO: implementar o nó cauda 
	//TODO: implementar as sentinelas
	
	NodoListaDuplamenteEncadeada<T> cabeca = null; // Estratégia usando marcação sentinela
	NodoListaDuplamenteEncadeada<T> cauda = null;// Estratégia usando marcação sentinela
	private int tamanhoAtual = 0;
	
	/**
	 * Constrói uma lista duplamente encadeada vazia, inicializando os nós sentinela.
	 */
	public ListaDuplamenteEncadeadaImpl() {// Estratégia usando marcação sentinela
		cabeca = new NodoListaDuplamenteEncadeada<T>();
		cauda = new NodoListaDuplamenteEncadeada<T>();
		cabeca.setProximo(cauda);
		cauda.setAnterior(cabeca);
		cabeca.setAnterior(null);
		cauda.setProximo(null);
		tamanhoAtual = 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEmpty() {
		return tamanhoAtual == 0; 
		// Ou, baseado nos sentinelas: return cabeca.getProximo() == cauda;
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
	public NodoListaDuplamenteEncadeada<T> search(T chave) {
		NodoListaDuplamenteEncadeada<T> atual = (NodoListaDuplamenteEncadeada<T>) cabeca.getProximo();
		while (atual != cauda) {
			if (atual.getChave() != null && atual.getChave().equals(chave)) {
				return atual;
			}
			atual = (NodoListaDuplamenteEncadeada<T>) atual.getProximo();
		}
		return null; // Não encontrado
	}

	/**
	 * {@inheritDoc}
	 * Insere o elemento no final da lista.
	 */
	@Override
	public void insert(T chave) {
		// Inserir no final da lista (antes do sentinela cauda)
		NodoListaDuplamenteEncadeada<T> novoNo = new NodoListaDuplamenteEncadeada<T>(chave);
		NodoListaDuplamenteEncadeada<T> penultimo = cauda.getAnterior();

		penultimo.setProximo(novoNo);
		novoNo.setAnterior(penultimo);
		novoNo.setProximo(cauda);
		cauda.setAnterior(novoNo);

		tamanhoAtual++;
	}

	/**
	 * {@inheritDoc}
	 * Remove a primeira ocorrência do elemento com a chave especificada.
	 * @return o nó removido, ou {@code null} se a chave não for encontrada.
	 * @throws ListaVaziaException se a lista estiver vazia antes da tentativa de remoção.
	 */
	@Override
	public NodoListaDuplamenteEncadeada<T> remove(T chave) {
		if (isEmpty()) {
			throw new ListaVaziaException();
		}

		NodoListaDuplamenteEncadeada<T> noParaRemover = search(chave);

		if (noParaRemover != null && noParaRemover != cabeca && noParaRemover != cauda) { // Certificar que não é sentinela
			NodoListaDuplamenteEncadeada<T> anterior = noParaRemover.getAnterior();
			NodoListaDuplamenteEncadeada<T> proximo = (NodoListaDuplamenteEncadeada<T>) noParaRemover.getProximo();

			anterior.setProximo(proximo);
			proximo.setAnterior(anterior);

			noParaRemover.setAnterior(null); // Limpar ponteiros do nó removido
			noParaRemover.setProximo(null);
			tamanhoAtual--;
			return noParaRemover;
		}
		return null; // Não encontrado ou é sentinela (não deveria acontecer para sentinela via search normal)
	}

	/**
	 * {@inheritDoc}
	 * Retorna uma representação em String dos elementos na ordem de inserção, separados por ", ".
	 */
	@Override
	public String imprimeEmOrdem() {
		StringBuilder sb = new StringBuilder();
		NodoListaDuplamenteEncadeada<T> atual = (NodoListaDuplamenteEncadeada<T>) cabeca.getProximo();
		while (atual != cauda) {
			sb.append(atual.getChave());
			if (atual.getProximo() != cauda) {
				sb.append(", ");
			}
			atual = (NodoListaDuplamenteEncadeada<T>) atual.getProximo();
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
		NodoListaDuplamenteEncadeada<T> atual = cauda.getAnterior();
		while (atual != cabeca) {
			sb.append(atual.getChave());
			if (atual.getAnterior() != cabeca) {
				sb.append(", ");
			}
			atual = atual.getAnterior();
		}
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 * Retorna o nó sucessor ao nó que contém a chave especificada.
	 * @return o nó sucessor, ou {@code null} se a chave não for encontrada ou não tiver sucessor.
	 */
	@Override
	public NodoListaDuplamenteEncadeada<T> sucessor(T chave) {
		NodoListaDuplamenteEncadeada<T> noEncontrado = search(chave);
		if (noEncontrado != null && noEncontrado.getProximo() != cauda) {
			return (NodoListaDuplamenteEncadeada<T>) noEncontrado.getProximo();
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * Retorna o nó predecessor ao nó que contém la chave especificada.
	 * @return o nó predecessor, ou {@code null} se a chave não for encontrada ou não tiver predecessor.
	 */
	@Override
	public NodoListaDuplamenteEncadeada<T> predecessor(T chave) {
		NodoListaDuplamenteEncadeada<T> noEncontrado = search(chave);
		if (noEncontrado != null && noEncontrado.getAnterior() != cabeca) {
			return noEncontrado.getAnterior();
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
		NodoListaDuplamenteEncadeada<T> atual = (NodoListaDuplamenteEncadeada<T>) cabeca.getProximo();
		int i = 0;
		while (atual != cauda) {
			array[i++] = atual.getChave();
			atual = (NodoListaDuplamenteEncadeada<T>) atual.getProximo();
		}
		return array;
	}

	/**
	 * {@inheritDoc}
	 * Insere o elemento no início da lista.
	 */
	@Override
	public void inserePrimeiro(T elemento) {
		// Inserir no início da lista (depois do sentinela cabeca)
		NodoListaDuplamenteEncadeada<T> novoNo = new NodoListaDuplamenteEncadeada<T>(elemento);
		NodoListaDuplamenteEncadeada<T> primeiroAtual = (NodoListaDuplamenteEncadeada<T>) cabeca.getProximo();

		novoNo.setProximo(primeiroAtual);
		if (primeiroAtual != null) { // Necessário se a lista puder estar vazia e primeiroAtual for o sentinela cauda
			primeiroAtual.setAnterior(novoNo);
		}
		cabeca.setProximo(novoNo);
		novoNo.setAnterior(cabeca);

		tamanhoAtual++;		
	}

	/**
	 * {@inheritDoc}
	 * @return o nó removido, ou {@code null} se a lista estiver vazia.
	 */
	@Override
	public NodoListaDuplamenteEncadeada<T> removeUltimo() {
		if (isEmpty()) {
			return null;
		}
		NodoListaDuplamenteEncadeada<T> ultimoNo = cauda.getAnterior();
		NodoListaDuplamenteEncadeada<T> penultimoNo = ultimoNo.getAnterior();

		penultimoNo.setProximo(cauda);
		cauda.setAnterior(penultimoNo);

		ultimoNo.setAnterior(null);
		ultimoNo.setProximo(null);
		tamanhoAtual--;
		return ultimoNo;
	}

	/**
	 * {@inheritDoc}
	 * @return o nó removido, ou {@code null} se a lista estiver vazia.
	 */
	@Override
	public NodoListaDuplamenteEncadeada<T> removePrimeiro() {
		if (isEmpty()) {
			return null;
		}
		NodoListaDuplamenteEncadeada<T> primeiroNo = (NodoListaDuplamenteEncadeada<T>) cabeca.getProximo();
		NodoListaDuplamenteEncadeada<T> segundoNo = (NodoListaDuplamenteEncadeada<T>) primeiroNo.getProximo();

		cabeca.setProximo(segundoNo);
		segundoNo.setAnterior(cabeca);

		primeiroNo.setAnterior(null);
		primeiroNo.setProximo(null);
		tamanhoAtual--;
		return primeiroNo;
	}

	/**
	 * {@inheritDoc}
	 * Este método não é suportado por esta implementação.
	 * @throws UnsupportedOperationException sempre.
	 */
	@Override
	public void insert(T chave, int index) {
		throw new UnsupportedOperationException("Operação de inserção por índice não suportada.");
	}
}
