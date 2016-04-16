package maw.org.wedding.map

interface DataStore<T> {
    fun exists(key: String): Boolean
    fun get(key: String): T?
    fun putOrUpdate(key: String, item: T)
}