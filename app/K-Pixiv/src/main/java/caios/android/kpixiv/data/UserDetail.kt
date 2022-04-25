package caios.android.kpixiv.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDetail(
    val profile: Profile = Profile(),
    @SerialName("profile_publicity")
    val profilePublicity: ProfilePublicity = ProfilePublicity(),
    val user: User = User(),
    val workspace: Workspace = Workspace()
) {
    @Serializable
    data class Profile(
        @SerialName("address_id")
        val addressId: Int = 0,
        @SerialName("background_image_url")
        val backgroundImageUrl: String = "",
        val birth: String = "",
        @SerialName("birth_day")
        val birthDay: String = "",
        @SerialName("birth_year")
        val birthYear: Int = 0,
        @SerialName("country_code")
        val countryCode: String = "",
        val gender: String = "",
        @SerialName("is_premium")
        val isPremium: Boolean = false,
        @SerialName("is_using_custom_profile_image")
        val isUsingCustomProfileImage: Boolean = false,
        val job: String = "",
        @SerialName("job_id")
        val jobId: Int = 0,
        @SerialName("pawoo_url")
        val pawooUrl: String = "",
        val region: String = "",
        @SerialName("total_follow_users")
        val totalFollowUsers: Int = 0,
        @SerialName("total_illust_bookmarks_public")
        val totalIllustBookmarksPublic: Int = 0,
        @SerialName("total_illust_series")
        val totalIllustSeries: Int = 0,
        @SerialName("total_illusts")
        val totalIllusts: Int = 0,
        @SerialName("total_manga")
        val totalManga: Int = 0,
        @SerialName("total_mypixiv_users")
        val totalMypixivUsers: Int = 0,
        @SerialName("total_novel_series")
        val totalNovelSeries: Int = 0,
        @SerialName("total_novels")
        val totalNovels: Int = 0,
        @SerialName("twitter_account")
        val twitterAccount: String = "",
        @SerialName("twitter_url")
        val twitterUrl: String = ""
    )

    @Serializable
    data class ProfilePublicity(
        @SerialName("birth_day")
        val birthDay: String = "",
        @SerialName("birth_year")
        val birthYear: String = "",
        val gender: String = "",
        val job: String = "",
        val pawoo: Boolean = false,
        val region: String = ""
    )

    @Serializable
    data class User(
        val account: String = "",
        val comment: String = "",
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

    @Serializable
    data class Workspace(
        val chair: String = "",
        val comment: String = "",
        val desk: String = "",
        val desktop: String = "",
        val monitor: String = "",
        val mouse: String = "",
        val music: String = "",
        val pc: String = "",
        val printer: String = "",
        val scanner: String = "",
        val tablet: String = "",
        val tool: String = "",
        @SerialName("workspace_image_url")
        val workspaceImageUrl: String = ""
    )
}