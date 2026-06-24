package com.trustcall.slee.sbb;

import javax.slee.ActivityContextInterface;
import javax.slee.Sbb;
import javax.slee.SbbContext;
import javax.slee.CreateException;
import javax.slee.RolledBackContext;

/**
 * Fraud Detection Service Building Block.
 */
public abstract class FraudDetectionSbb implements Sbb {

    private SbbContext sbbContext;

    public void checkFraudReports(String caller) {
        System.out.println("FraudDetectionSbb: Checking fraud reports for " + caller);
    }

    public void setSbbContext(SbbContext context) { this.sbbContext = context; }
    public void unsetSbbContext() { this.sbbContext = null; }

    public void sbbCreate() throws CreateException {}
    public void sbbPostCreate() throws CreateException {}
    public void sbbActivate() {}
    public void sbbPassivate() {}
    public void sbbLoad() {}
    public void sbbStore() {}
    public void sbbRemove() {}
    public void sbbExceptionThrown(Exception exception, Object event, ActivityContextInterface aci) {}
    public void sbbRolledBack(RolledBackContext context) {}
}
