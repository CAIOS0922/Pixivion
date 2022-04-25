package caios.android.kpixiv.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserPreview(
    val illusts: List<Illust> = listOf(),
    @SerialName("is_muted")
    val isMuted: Boolean = false,
    val user: User = User()
) {
    @Serializable
    data class Illust(
        val caption: String = "",
        @SerialName("create_date")
        val createDate: String = "",
        val height: Int = 0,
        val id: Int = 0,
        @SerialName("image_urls")
        val imageUrls: ImageUrls = ImageUrls(),
        @SerialName("is_bookmarked")
        val isBookmarked: Boolean = false,
        @SerialName("is_muted")
        val isMuted: Boolean = false,
        @SerialName("meta_pages")
        val metaPages: List<MetaPage> = listOf(),
        @SerialName("meta_single_page")
        val metaSinglePage: MetaSinglePage = MetaSinglePage(),
        @SerialName("page_count")
        val pageCount: Int = 0,
        val restrict: Int = 0,
        @SerialName("sanity_level")
        val sanityLevel: Int = 0,
        val tags: List<Tag> = listOf(),
        val title: String = "",
        val tools: List<String> = listOf(),
        @SerialName("total_bookmarks")
        val totalBookmarks: Int = 0,
        @SerialName("total_view")
        val totalView: Int = 0,
        val type: String = "",
        val user: User = User(),
        val visible: Boolean = false,
        val width: Int = 0,
        @SerialName("x_restrict")
        val xRestrict: Int = 0
    ) {
        @Serializable
        data class ImageUrls(
            val large: String = "",
            val medium: String = "",
            @SerialName("square_medium")
            val squareMedium: String = ""
        )

        @Serializable
        data class MetaPage(
            @SerialName("image_urls")
            val imageUrls: ImageUrls = ImageUrls()
        ) {
            @Serializable
            data class ImageUrls(
                val large: String = "",
                val medium: String = "",
                val original: String = "",
                @SerialName("square_medium")
                val squareMedium: String = ""
            )
        }

        @Serializable
        data class MetaSinglePage(
            @SerialName("original_image_url")
            val originalImageUrl: String = ""
        )

        @Serializable
        data class Tag(
            val name: String = "",
            @SerialName("translated_name")
            val translatedName: String? = null
        )

        @Serializable
        data class User(
            val account: String = "",
            val id: Int = 0,
            @SerialName("is_followed")
            val isFollowed: Boolean = false,
            val name: String = "",
            @SerialName("profile_image_urls")
            val profileImageUrls: ProfileImageUrls = ProfileImageUrls()
        ) {
            @Serializable
            data class ProfileImageUrls(
                val medium: String = ""
            )
        }
    }

    @Serializable
    data class User(
        val account: String = "",
        val id: Int = 0,
        @SerialName("is_followed")
        val isFollowed: Boolean = false,
        val name: String = "",
        @SerialName("profile_image_urls")
        val profileImageUrls: ProfileImageUrls = ProfileImageUrls()
    ) {
        @Serializable
        data class ProfileImageUrls(
            val medium: String = ""
        )
    }
}