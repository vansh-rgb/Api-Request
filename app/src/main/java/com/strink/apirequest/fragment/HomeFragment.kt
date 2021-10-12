package com.strink.apirequest.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.strink.apirequest.R
import com.strink.apirequest.adapter.DetailAdapter
import com.strink.apirequest.model.Detail
import com.strink.apirequest.util.ConnectionManager
import org.json.JSONException


class HomeFragment : Fragment() {

    private lateinit var recyclerDetail: RecyclerView
    private lateinit var detailAdapter: DetailAdapter
    private var detailList = arrayListOf<Detail>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        setupRecycler(view)
        return (view)
    }

    private fun setupRecycler(view: View) {
        recyclerDetail = view.findViewById(R.id.detailRecycler)
        val queue = Volley.newRequestQueue(activity as Context)
        val url = "https://gorest.co.in/public-api/users"
        if (ConnectionManager().isNetworkAvailable(activity as Context)) {
            val jsonRequestObject = object : JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                Response.Listener { response ->
                    try {
                        val resArray = response.getJSONArray("data")

                        for(i in 0 until resArray.length()) {
                            val resObject = resArray.getJSONObject(i)
                            val detail = Detail(
                                resObject.getString("id"),
                                resObject.getString("name"),
                                resObject.getString("email"),
                                resObject.getString("gender"),
                                resObject.getString("status")
                            )
                            detailList.add(detail)
                            if(activity!=null) {
                                detailAdapter = DetailAdapter(detailList, activity as Context)
                                recyclerDetail.layoutManager = LinearLayoutManager(activity as Context)
                                recyclerDetail.adapter = detailAdapter
                                recyclerDetail.setHasFixedSize(true)
                            }
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener {
                    Toast.makeText(activity as Context, it?.message, Toast.LENGTH_SHORT).show()
                }
            ) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-type"] = "application/json"
                    return headers
                }
            }
            queue.add(jsonRequestObject)
        } else {
            val builder = AlertDialog.Builder(activity as Context)
            builder.setTitle("Error")
            builder.setMessage("No Internet Connection found. Please connect to the internet and re-open the app.")
            builder.setCancelable(false)
            builder.setPositiveButton("Ok") { _, _ ->
                ActivityCompat.finishAffinity(activity as Activity)
            }
            builder.create()
            builder.show()
        }
    }


}