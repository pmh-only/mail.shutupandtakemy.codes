package codes.pmh.sutmc.mail.entity;

public class APIAddRule {
    private APIAddRuleAction[] actions;
    private boolean enabled;
    private APIAddRuleMatcher[] matchers;
    private String name;
    private int priority;

    public APIAddRuleAction[] getActions() {
        return actions;
    }

    public void setActions(APIAddRuleAction[] actions) {
        this.actions = actions;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public APIAddRuleMatcher[] getMatchers() {
        return matchers;
    }

    public void setMatchers(APIAddRuleMatcher[] matchers) {
        this.matchers = matchers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
