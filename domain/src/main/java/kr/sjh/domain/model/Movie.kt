package kr.sjh.domain.model

import android.net.Uri
import android.util.Log
import java.net.URLDecoder

data class Movie(
    val title: String, // "튜브펫 <b>주식</b>회사",
    val link: String, // "https://movie.naver.com/movie/bi/mi/basic.nhn?code=213271",
    val image: String, // "https://ssl.pstatic.net/imgmovie/mdi/mit110/2132/213271_P01_101816.jpg",
    val subtitle: String, // "Tubepet Company",
    val pubDate: String, // "2021",
    val director: String, // "송상민|곽지성|",
    val actor: String, // "김진근|영민|곽수진|박시안|김대성|이광희|김소율|나현정|",
    val userRating: String // "6.81"
) {
    fun getCode(link: String?): String {
        return link?.let {
            val uri = Uri.parse(link)
            var code = uri.getQueryParameter("code")
            code
        }.toString()
    }
}