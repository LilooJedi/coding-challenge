package io.bankbridge.handler;

/**
 * An abstract class for different versions of bank handlers.
 * Implements the Handleable interface.
 *
 * @see Handleable
 */
public abstract class BankHandler implements Handleable {

    /**
     * The version of the bank handler.
     */
    Version version;

    /**
     * Constructor for specified version.
     *
     * @param version the version of the bank handler, as a Version
     * @see Version
     */
    BankHandler(Version version) {
        this.version = version;
    }

    /**
     * Returns this bank handler's url path matching it's version.
     *
     * @return the path, as a String.
     * @see Version#getPath()
     */
    public String getPath() {
        return version.getPath();
    }
}
