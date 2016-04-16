package maw.org.wedding.map

import java.util.*

class MarkerViewModelStore: DataStore<MarkerViewModel> {

    val map: HashMap<String, MarkerViewModel> = HashMap()

    override fun putOrUpdate(key: String, item: MarkerViewModel) {
        map.put(key, item)
    }

    override fun get(key: String): MarkerViewModel? {
        return map[key]
    }

    override fun exists(key: String): Boolean {
        return map.containsKey(key)
    }
}

