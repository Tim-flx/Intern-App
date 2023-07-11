package com.dicoding.internapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import kotlin.math.log

class ThirdActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private val userList = mutableListOf<User>()
    private val userAdapter = UserAdapter(object: UserAdapter.OnUserClickListener {
        override fun onUserClick(user: User) {
            val userName = "${user.first_name} ${user.last_name}"
            val intent = Intent(this@ThirdActivity, SecondActivity::class.java)
            intent.putExtra("user_name", userName)
            startActivity(intent)
        }
    })
    private var currentPage = 1
    private var totalPages = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        toolbar = findViewById(R.id.custom_toolbar)

        setSupportActionBar(toolbar)

        val backButton = toolbar.findViewById<ImageView>(R.id.back_button)
        val titleText = toolbar.findViewById<TextView>(R.id.title_text)

        backButton.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        titleText.text = "Third Screen"

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = userAdapter

        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                    if (currentPage < totalPages) {
                        loadNextPage()
                    }
                }
            }
        })

        swipeRefreshLayout.setOnRefreshListener {
            userList.clear()
            currentPage = 1
            totalPages = 0
            fetchUserData(1)
            swipeRefreshLayout.isRefreshing = false
        }

        fetchUserData(1)
    }

    fun fetchUserData(page: Int) {
        val emptyTextView = findViewById<TextView>(R.id.empty_text_view)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://reqres.in/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val userService = retrofit.create(UserService::class.java)

        val call = userService.getUsers(page)

        call.enqueue(object: Callback<UsersResponse> {
            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                if (response.isSuccessful) {
                    val usersResponse = response.body()
                    if (usersResponse != null) {
                        totalPages = usersResponse.total_pages
                        userList.addAll(usersResponse.data)
                        userAdapter.setUserList(userList)
                        if (userList.isEmpty()) {
                            emptyTextView.visibility = View.VISIBLE
                        } else {
                            emptyTextView.visibility = View.GONE
                            Log.d("Tes", "Masuk")

                        }
                    }
                }
            }

            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                // handle failure
            }
        })
    }

    fun loadNextPage() {
        currentPage++
        fetchUserData(currentPage)
    }

    interface UserService {
        @GET("/api/users")
        fun getUsers(@Query("page") page: Int): Call<UsersResponse>
    }

    data class UsersResponse(
        val page: Int,
        val per_page: Int,
        val total: Int,
        val total_pages: Int,
        val data: List<User>
    )
}

