package com.farmatodo.data

import java.io.Serializable

abstract class ServiceResult{
    val code : Int? = 0
    val status = ""
    abstract val data : Any?
}

data class Thumbnail(val path:String, val extension:String)

abstract class BaseData : Serializable{
    val id : Int = 0
    val title : String? = ""
    val description : String? = ""
    val thumbnail: Thumbnail? = null
}

//characters
class Character(val name: String?) : BaseData()
class CharacterData {
    val results : MutableList<Character> = mutableListOf()
}
class CharacterResult : ServiceResult(){
    override var data: CharacterData? = null
}

//comics
class Comic : BaseData()
data class ComicData(val results : MutableList<Comic> = mutableListOf())
class ComicResult : ServiceResult(){
    override var data: ComicData? = null
}

//story
class Story : BaseData()
data class StoryData (val results : MutableList<Story> = mutableListOf())
class StoryResult : ServiceResult(){
    override var data: StoryData? = null
}

//events
class Event : BaseData()
data class EventData (val results : MutableList<Event> = mutableListOf())
class EventResult : ServiceResult(){
    override var data: EventData? = null
}

//series
class Serie : BaseData()
data class SerieData (val results : MutableList<Serie> = mutableListOf())
class SerieResult : ServiceResult(){
    override var data: SerieData? = null
}

//creators
data class Creator(val fullName : String?) : BaseData()
data class CreatorData (val results : MutableList<Creator> = mutableListOf())
class CreatorResult : ServiceResult(){
    override var data: CreatorData? = null
}


