package tad.conjuntoDinamico;

/**
 * Implementação de um conjunto dinâmico de inteiros usando um array.
 * O conjunto permite a inserção de elementos, remoção, busca, e outras operações
 * como encontrar o mínimo, máximo, predecessor e sucessor baseados na ordem de inserção.
 * O array interno cresce dinamicamente conforme necessário.
 */
public class MeuConjuntoDinamico implements ConjuntoDinamicoIF<Integer>{

	
	private static final int TAMANHO_INICIAL = 10;
	private Integer[] meusDados = null;
	private int posInsercao = 0;
	
	/**
	 * Constrói um novo conjunto dinâmico com capacidade inicial padrão.
	 */
	public MeuConjuntoDinamico() {
		meusDados = new Integer[TAMANHO_INICIAL];
	}

	/**
	 * {@inheritDoc}
	 * Insere o item no final do conjunto. Se o array interno estiver cheio,
	 * ele é redimensionado.
	 */
	@Override
	public void inserir(Integer item) {
		if (meusDados == null) {
			meusDados = new Integer[TAMANHO_INICIAL];
		}
		if (posInsercao == meusDados.length) {
			meusDados = aumentarArray();
		}
		meusDados[posInsercao] = item;
		posInsercao++;
	}
	
	/**
	 * Dobra o tamanho do array interno que armazena os elementos do conjunto.
	 * Os elementos existentes são copiados para o novo array.
	 * @return Um novo array com o dobro da capacidade e os elementos do array antigo.
	 */
	private Integer[] aumentarArray() {
		int novoTamanho = (meusDados.length == 0) ? TAMANHO_INICIAL : meusDados.length * 2;
		Integer[] arrayMaior = new Integer[novoTamanho];
		for (int i = 0; i < posInsercao; i++) { // Corrigido para copiar até posInsercao
			arrayMaior[i] = meusDados[i];
		}
		return arrayMaior;
	}

	/**
	 * {@inheritDoc}
	 * Remove a primeira ocorrência do item especificado do conjunto.
	 * Os elementos subsequentes são deslocados para a esquerda.
	 * @throws RuntimeException se o item não for encontrado no conjunto.
	 */
	@Override
	public Integer remover(Integer item) {
		int indiceRemover = -1;
		for (int i = 0; i < posInsercao; i++) {
			if (meusDados[i].equals(item)) {
				indiceRemover = i;
				break;
			}
		}

		if (indiceRemover == -1) {
			throw new RuntimeException("Elemento não encontrado para remoção"); 
		}

		Integer itemRemovido = meusDados[indiceRemover];

		for (int i = indiceRemover; i < posInsercao - 1; i++) {
			meusDados[i] = meusDados[i + 1];
		}
		meusDados[posInsercao - 1] = null; 
		posInsercao--;

		return itemRemovido;
	}

	/**
	 * {@inheritDoc}
	 * Retorna o elemento inserido imediatamente antes do item especificado.
	 * @param item o item cujo predecessor é buscado.
	 * @return o item predecessor, ou {@code null} se o item especificado for o primeiro
	 *         elemento inserido ou não existir um predecessor direto na ordem de inserção.
	 * @throws RuntimeException se o conjunto estiver vazio ou se o item não for encontrado.
	 */
	@Override
	public Integer predecessor(Integer item) {
		if (posInsercao == 0) {
			throw new RuntimeException("Conjunto vazio");
		}
		int indiceItem = -1;
		for (int i = 0; i < posInsercao; i++) {
			if (meusDados[i].equals(item)) {
				indiceItem = i;
				break;
			}
		}

		if (indiceItem == -1) {
			throw new RuntimeException("Elemento não encontrado");
		}

		if (indiceItem == 0) {
			return null;
		}
		return meusDados[indiceItem - 1];
	}

	/**
	 * {@inheritDoc}
	 * Retorna o elemento inserido imediatamente após o item especificado.
	 * @param item o item cujo sucessor é buscado.
	 * @return o item sucessor, ou {@code null} se o item especificado for o último
	 *         elemento inserido ou não existir um sucessor direto na ordem de inserção.
	 * @throws RuntimeException se o conjunto estiver vazio ou se o item não for encontrado.
	 */
	@Override
	public Integer sucessor(Integer item) {
		if (posInsercao == 0) {
			throw new RuntimeException("Conjunto vazio");
		}
		int indiceItem = -1;
		for (int i = 0; i < posInsercao; i++) {
			if (meusDados[i].equals(item)) {
				indiceItem = i;
				break;
			}
		}

		if (indiceItem == -1) {
			throw new RuntimeException("Elemento não encontrado");
		}

		if (indiceItem == posInsercao - 1) {
			return null;
		}
		return meusDados[indiceItem + 1];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int tamanho() {
		return posInsercao;
	}

	/**
	 * {@inheritDoc}
	 * @throws RuntimeException se o item não for encontrado no conjunto.
	 */
	@Override
	public Integer buscar(Integer item) {
		for (int i = 0; i < posInsercao; i++) {
			if (meusDados[i].equals(item)) {
				return meusDados[i];
			}
		}
		throw new RuntimeException("Elemento não encontrado"); 
	}

	/**
	 * {@inheritDoc}
	 * @throws RuntimeException se o conjunto estiver vazio.
	 */
	@Override
	public Integer minimum() {
		if (posInsercao == 0) {
			throw new RuntimeException("Conjunto vazio"); 
		}
		Integer minimo = meusDados[0];
		for (int i = 1; i < posInsercao; i++) {
			if (meusDados[i].compareTo(minimo) < 0) {
				minimo = meusDados[i];
			}
		}
		return minimo;
	}

	/**
	 * {@inheritDoc}
	 * @throws RuntimeException se o conjunto estiver vazio.
	 */
	@Override
	public Integer maximum() {
		if (posInsercao == 0) {
			throw new RuntimeException("Conjunto vazio"); 
		}
		Integer maximo = meusDados[0];
		for (int i = 1; i < posInsercao; i++) {
			if (meusDados[i].compareTo(maximo) > 0) {
				maximo = meusDados[i];
			}
		}
		return maximo;
	}

}
