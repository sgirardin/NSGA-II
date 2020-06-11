package ch.fhnw.mbis.aci.nsgaii.datastructure;

import com.debacharya.nsgaii.datastructure.AbstractAllele;

public class IntegerAllele extends AbstractAllele {

    public IntegerAllele(Integer gene) {
        super(gene);
    }

    @Override
    public Integer getAllele() {
        return (int) this.allele;
    }

    @Override
    public IntegerAllele getCopy() {
        return new IntegerAllele((Integer)this.allele);
    }

    @Override
    public String toString() {
        return "IntegerAllele{" +
                "gene=" + allele +
                '}';
    }
}
