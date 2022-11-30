package codes.pmh.sutmc.mail.entity;

public class APIAddRuleResponse {
    private boolean enabled;
    private String tag;
    private String name;

    private int priority;

    private APIAddRuleMatcher[] matchers;
    private APIAddRuleAction[] actions;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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

    public APIAddRuleMatcher[] getMatchers() {
        return matchers;
    }

    public void setMatchers(APIAddRuleMatcher[] matchers) {
        this.matchers = matchers;
    }

    public APIAddRuleAction[] getActions() {
        return actions;
    }

    public void setActions(APIAddRuleAction[] actions) {
        this.actions = actions;
    }
}
