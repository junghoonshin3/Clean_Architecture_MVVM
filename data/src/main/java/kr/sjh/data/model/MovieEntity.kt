package kr.sjh.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val _id :Int,
    val _title: String, // "튜브펫 <b>주식</b>회사",
    val _link: String, // "https://movie.naver.com/movie/bi/mi/basic.nhn?code=213271",
    val _image: String, // "https://ssl.pstatic.net/imgmovie/mdi/mit110/2132/213271_P01_101816.jpg",
    val _subtitle: String, // "Tubepet Company",
    val _pubDate: String, // "2021",
    val _director: String, // "송상민|곽지성|",
    val _actor: String, // "김진근|영민|곽수진|박시안|김대성|이광희|김소율|나현정|",
    val _userRating: String, // "6.81"
    val _code: Int
)