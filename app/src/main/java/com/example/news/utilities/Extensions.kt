package com.example.news.utilities

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.news.R
import java.net.URL
import java.util.concurrent.TimeUnit

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

@BindingAdapter("loadImage")
fun ImageView.loadImage(url: String?) {
    Glide
        .with(this)
        .load(url)
        .centerCrop().placeholder(R.drawable.ic_launcher_background)
        .into(this)
}

@BindingAdapter("setDomainNameFromUrl")
fun TextView.setDomainNameFromUrl(urlString: String?) {
    val domain = getDomainNameFromUrl(urlString)
    this.text = domain
}

@BindingAdapter("setElapsedTime")
fun TextView.setElapsedTime(timestamp: Long?) {
    timestamp?.let {
        val currentTime = System.currentTimeMillis()
        val elapsedTime = currentTime - it

        val seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime)
        val hours = TimeUnit.MILLISECONDS.toHours(elapsedTime)
        val days = TimeUnit.MILLISECONDS.toDays(elapsedTime)

        val elapsedTimeString = when {
            days > 0 -> "$days days ago"
            hours > 0 -> "$hours hours ago"
            minutes > 0 -> "$minutes minutes ago"
            else -> "$seconds seconds ago"
        }

        text = elapsedTimeString
    }
}

@BindingAdapter("setEllipsizedTextIfLong")
fun TextView.setEllipsizedTextIfLong(text: String) {
    if (text.split("\\s+".toRegex()).size >= 10) {
        val words = text.split("\\s+".toRegex()).take(10)
        val truncatedText = words.joinToString(" ")
        this.text = "$truncatedText..."
    } else {
        this.text = text
    }
}



private fun getDomainNameFromUrl(urlString: String?): String {
    urlString?.let {
        try {
            val url = URL(it)
            var host = url.host
            // Remove 'www.' prefix if present
            if (host.startsWith("www.")) {
                host = host.substring(4)
            }
            return host
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return ""
}
