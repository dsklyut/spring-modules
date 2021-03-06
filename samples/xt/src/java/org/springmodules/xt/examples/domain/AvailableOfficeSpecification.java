package org.springmodules.xt.examples.domain;

/**
 * BaseSpecification determining if a given office has available room.
 * 
 * @author Sergio Bossa
 */
public class AvailableOfficeSpecification implements BaseSpecification<IOffice> {
    
    private int limit = IOffice.MAX_EMPLOYEES;
    
    public boolean isSatisfiedBy(IOffice o) {
        return o.getEmployees().size() < this.limit; 
    }
}
