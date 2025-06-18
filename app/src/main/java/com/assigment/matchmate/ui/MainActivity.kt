package com.assigment.matchmate.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.assigment.matchmate.MatchMateApplication
import com.assigment.matchmate.R
import com.assigment.matchmate.ui.adapter.UserAdapter
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var errorText: TextView
    private lateinit var retryButton: Button
    private lateinit var toggleImagesButton: Button

    private lateinit var userAdapter: UserAdapter
    private var showImages = true

    private val viewModel: UserViewModel by viewModels {
        UserViewModelFactory((application as MatchMateApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupViews() {
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        errorText = findViewById(R.id.errorText)
        retryButton = findViewById(R.id.retryButton)
        toggleImagesButton = findViewById(R.id.toggleImagesButton)

        retryButton.setOnClickListener {
            viewModel.refreshData()
        }

        toggleImagesButton.setOnClickListener {
            toggleImageDisplay()
        }
    }

    private fun setupRecyclerView() {
        userAdapter = UserAdapter(
            onAccept = { userId -> viewModel.acceptUser(userId) },
            onDecline = { userId -> viewModel.declineUser(userId) },
            showImages = showImages
        )

        recyclerView.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun observeViewModel() {
        viewModel.users.observe(this) { users ->
            userAdapter.submitList(users)
        }

        viewModel.loading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(this) { error ->
            if (error != null) {
                errorText.visibility = View.VISIBLE
                retryButton.visibility = View.VISIBLE
                errorText.text = error

                Snackbar.make(recyclerView, error, Snackbar.LENGTH_LONG)
                    .setAction("Dismiss") { viewModel.clearError() }
                    .show()
            } else {
                errorText.visibility = View.GONE
                retryButton.visibility = View.GONE
            }
        }

        viewModel.retryCount.observe(this) { count ->
            if (count > 1) {
                Snackbar.make(recyclerView, "Retry attempt $count", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun toggleImageDisplay() {
        showImages = !showImages
        toggleImagesButton.text = if (showImages) "Hide Images" else "Show Images"

        userAdapter = UserAdapter(
            onAccept = { userId -> viewModel.acceptUser(userId) },
            onDecline = { userId -> viewModel.declineUser(userId) },
            showImages = showImages
        )
        recyclerView.adapter = userAdapter

        viewModel.users.value?.let { users ->
            userAdapter.submitList(users)
        }
    }
}