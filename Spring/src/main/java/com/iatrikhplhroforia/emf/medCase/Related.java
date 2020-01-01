package com.iatrikhplhroforia.emf.medCase;

public class Related<P, C> {
    private P person;
    private C cases;

    public Related(P person, C cases) {
        this.person = person;
        this.cases = cases;
    }

    public P getPerson() {
        return person;
    }

    public void setPerson(P person) {
        this.person = person;
    }

    public C getCases() {
        return cases;
    }

    public void setCases(C cases) {
        this.cases = cases;
    }
}
