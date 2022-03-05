package ulaval.glo2003;

import java.util.List;

public class AndCriteria implements Criteria{
    private Criteria criteria;
    private Criteria otherCriteria;

    public AndCriteria(Criteria p_criteria, Criteria p_otherCriteria) {
        this.criteria = p_criteria;
        this.otherCriteria = p_otherCriteria;
    }

    @Override
    public List<Product> meetCriteria(List<Product> products) {
        List<Product> firstCriteriaProducts = criteria.meetCriteria(products);
        return otherCriteria.meetCriteria(firstCriteriaProducts);
    }
}
