package garage;

import java.io.Serializable;

public class Entry<A, B> implements Serializable {
	private static final long serialVersionUID = -326304399670095552L;
	A a;
	B b;

	public Entry(A a, B b) {
		this.a = a;
		this.b = b;
	}

	public void setA(A a) {
		this.a = a;
	}

	public void setB(B b) {
		this.b = b;
	}

	public A getA() {
		return a;
	}

	public B getB() {
		return b;
	}

	@SuppressWarnings("unchecked")
	public boolean equals(Object o) {
		if (a.equals(((Entry<A, B>) o).getA())) {
			return true;
		}
		return false;
	}
}



