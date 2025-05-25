package tad.pilha;

public class PilhaCheiaException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public PilhaCheiaException() {
		super("pilha cheia!");
	}
	
}
