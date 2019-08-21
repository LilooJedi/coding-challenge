package io.bankbridge.handler;

/**
 * An interface for anything handle-able.
 */
public interface Handleable {

    /**
     * Handlers usually require an init method to load some config file.
     */
    void init();

    /**
     * Returns the String representation of the list of banks with their names and ids, in the json format.
     * {@code
     * ```json
     * [
     * {
     * "name": "Credit Sweets",
     * "id": "5678"
     * },
     * {
     * "name": "Banco de espiritu santo",
     * "id": "9870"
     * },
     * {
     * "name": "Royal Bank of Boredom",
     * "id": "1234"
     * }
     * ]
     * ```}
     *
     * @return the list of banks with their names and ids, in the json format, as a String.
     */
    String handle();

}
