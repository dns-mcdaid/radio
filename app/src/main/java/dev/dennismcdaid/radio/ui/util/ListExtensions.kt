package dev.dennismcdaid.radio.ui.util

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

inline fun <reified T> diffCallback(crossinline comparison: (oldItem: T, newItem: T) -> Boolean) =
    object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return comparison(oldItem, newItem)
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }

    }