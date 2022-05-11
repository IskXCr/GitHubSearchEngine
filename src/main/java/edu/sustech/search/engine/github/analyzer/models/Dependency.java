package edu.sustech.search.engine.github.analyzer.models;

public class Dependency {
    private final String groupId;
    private final String artifactId;
    private final String version;

    public Dependency(String groupId, String artifactId, String version) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    @Override
    public String toString() {
        return "<groupId>" + groupId + "</groupId>" + "<artifactId>" + artifactId + "</artifactId>" + (version == null ? null : "<version>" + version + "</version>");
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof Dependency d) {
            return groupId.equals(d.groupId) && artifactId.equals(d.artifactId) && version.equals(d.version);
        }
        return false;
    }
}
