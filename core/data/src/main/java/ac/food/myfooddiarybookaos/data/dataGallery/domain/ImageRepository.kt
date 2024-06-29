package ac.food.myfooddiarybookaos.data.dataGallery.domain

import ac.food.myfooddiarybookaos.model.image.GalleryImage

interface ImageRepository {

    fun getAllPhotos(
        page: Int,
        loadSize: Int,
        currentLocation: String? = null,
    ): MutableList<GalleryImage>

    fun getFolderList(): ArrayList<String>
}
