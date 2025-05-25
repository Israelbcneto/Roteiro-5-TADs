package tad.listasEncadeadas;

import java.util.Objects;


public class NodoListaDuplamenteEncadeada<T extends Comparable<T>> extends NodoListaEncadeada<T>{
    private NodoListaDuplamenteEncadeada<T> anterior;

    public NodoListaDuplamenteEncadeada(T chave) {
        super(chave); // Inicializa 'chave' e 'proximo' (presumivelmente para null) na superclasse
        this.anterior = null;
    }

    // Construtor para nós sentinela (chave é null)
    public NodoListaDuplamenteEncadeada() {
        super(null); 
        this.anterior = null;
    }

    public NodoListaDuplamenteEncadeada<T> getAnterior() {
        return anterior;
    }

    public void setAnterior(NodoListaDuplamenteEncadeada<T> anterior) {
        this.anterior = anterior;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        

        NodoListaDuplamenteEncadeada<?> that = (NodoListaDuplamenteEncadeada<?>) o;

        return Objects.equals(getChave(), that.getChave());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getChave());
    }

    @Override
    public String toString() {

        return getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(this)) + "[" + getChave() + "]";
    }
}