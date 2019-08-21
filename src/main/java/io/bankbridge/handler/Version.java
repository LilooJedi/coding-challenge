package io.bankbridge.handler;

/**
 * An enum to manage versions of bank handlers.
 * Each version is associated with an http path and the name of a resource/config file.
 */
public enum Version {

    V1("/v1/banks/all", "banks-v1.json"),
    V2("/v2/banks/all", "banks-v2.json");

    /**
     * The http path on which to see the corresponding version.
     */
    private String path;

    /**
     * The name of the resource/config file required for this version.
     */
    private String resourceName;

    /**
     * Enum constructor
     *
     * @param path         The http path on which to see the corresponding version, as a String
     * @param resourceName The name of the resource/config file required for this version, as a String.
     */
    Version(String path, String resourceName) {
        this.path = path;
        this.resourceName = resourceName;
    }

    /**
     * Version path accessor
     *
     * @return the path, as a String.
     */
    public String getPath() {
        return path;
    }

    /**
     * Version resource accessor.
     *
     * @return the name of the resource, as a String.
     */
    public String getResourceName() {
        return resourceName;
    }
}