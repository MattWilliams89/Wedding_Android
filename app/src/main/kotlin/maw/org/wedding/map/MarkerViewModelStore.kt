package maw.org.wedding.map

class MarkerViewModelStore: DataStore<MarkerViewModel> {

    val map: MutableMap<String, MarkerViewModel> = hashMapOf()

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

