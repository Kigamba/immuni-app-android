package org.ascolto.onlus.geocrowd19.android.ui.home.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.bendingspoons.base.extensions.setTint
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import org.ascolto.onlus.geocrowd19.android.AscoltoApplication
import org.ascolto.onlus.geocrowd19.android.R
import org.ascolto.onlus.geocrowd19.android.models.survey.Severity
import org.ascolto.onlus.geocrowd19.android.ui.home.home.model.*

class HomeListAdapter(val clickListener: HomeClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val context = AscoltoApplication.appContext
    var items = mutableListOf<HomeItemType>()

    val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(false).build()

    inner class SurveyCardVH(v: LinearLayout) : RecyclerView.ViewHolder(v), View.OnClickListener {
        var bottomMessage: TextView = v.findViewById(R.id.bottomMessage)
        override fun onClick(v: View) {
            clickListener.onClick(items[adapterPosition])
        }
    }

    inner class HeaderCardVH(v: LinearLayout) : RecyclerView.ViewHolder(v) {

    }

    inner class GeolocationCardVH(v: ConstraintLayout) : RecyclerView.ViewHolder(v), View.OnClickListener {

        override fun onClick(v: View) {
            clickListener.onClick(items[adapterPosition])
        }
    }

    inner class NotificationsCardVH(v: ConstraintLayout) : RecyclerView.ViewHolder(v), View.OnClickListener {

        override fun onClick(v: View) {
            clickListener.onClick(items[adapterPosition])
        }
    }

    inner class SuggestionsCardWhiteVH(v: ConstraintLayout) : RecyclerView.ViewHolder(v), View.OnClickListener {
        var container: ConstraintLayout = v
        var title: TextView = v.findViewById(R.id.title)
        var icon: ImageView = v.findViewById(R.id.icon)
        override fun onClick(v: View) {
            clickListener.onClick(items[adapterPosition])
        }
    }

    inner class SuggestionsCardYellowVH(v: ConstraintLayout) : RecyclerView.ViewHolder(v), View.OnClickListener {
        var container: ConstraintLayout = v
        var title: TextView = v.findViewById(R.id.title)
        var icon: ImageView = v.findViewById(R.id.icon)
        override fun onClick(v: View) {
            clickListener.onClick(items[adapterPosition])
        }
    }

    inner class SuggestionsCardRedVH(v: ConstraintLayout) : RecyclerView.ViewHolder(v), View.OnClickListener {
        var container: ConstraintLayout = v
        var title: TextView = v.findViewById(R.id.title)
        var icon: ImageView = v.findViewById(R.id.icon)
        override fun onClick(v: View) {
            clickListener.onClick(items[adapterPosition])
        }
    }


    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.itemView.setOnClickListener(holder as? View.OnClickListener)
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.setOnClickListener(null)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == 0) {
            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.home_survey_card_item, parent, false)

            return SurveyCardVH(v as LinearLayout)
        }
        else if(viewType == 1) {
            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.home_geolocation_card_item, parent, false)

            return GeolocationCardVH(v as ConstraintLayout)
        }
        else if(viewType == 2) {
            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.home_notifications_card_item, parent, false)

            return NotificationsCardVH(v as ConstraintLayout)
        }
        else if(viewType == 3) {
            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.home_header_card_item, parent, false)

            return HeaderCardVH(v as LinearLayout)
        }
        else if(viewType == 4) {
            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.home_suggestions_card_white_item, parent, false)

            return SuggestionsCardWhiteVH(v as ConstraintLayout)
        }
        else if(viewType == 5) {
            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.home_suggestions_card_yellow_item, parent, false)

            return SuggestionsCardYellowVH(v as ConstraintLayout)
        }
        else {
            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.home_suggestions_card_red_item, parent, false)

            return SuggestionsCardRedVH(v as ConstraintLayout)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {}

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payload: List<Any>
    ) {
        when(holder) {
            is SuggestionsCardWhiteVH -> {
                val item = items[position] as SuggestionsCardWhite
                holder.title.text = HtmlCompat.fromHtml(item.title, HtmlCompat.FROM_HTML_MODE_LEGACY)
            }
            is SuggestionsCardYellowVH -> {
                val item = items[position] as SuggestionsCardYellow
                holder.title.text = HtmlCompat.fromHtml(item.title, HtmlCompat.FROM_HTML_MODE_LEGACY)
            }
            is SuggestionsCardRedVH -> {
                val item = items[position] as SuggestionsCardRed
                holder.title.text = HtmlCompat.fromHtml(item.title, HtmlCompat.FROM_HTML_MODE_LEGACY)
            }
            is SurveyCardVH -> {
                holder.bottomMessage.text = HtmlCompat.fromHtml("Hai <font color=\"#6993FF\"><b>5 diari clinici</b></font> da compilare", HtmlCompat.FROM_HTML_MODE_LEGACY)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(items[position]) {
            is SurveyCard -> 0
            is EnableGeolocationCard -> 1
            is EnableNotificationCard -> 2
            is HeaderCard -> 3
            is SuggestionsCardWhite -> 4
            is SuggestionsCardYellow -> 5
            is SuggestionsCardRed -> 6
        }
    }

    override fun getItemCount(): Int = items.size
}

interface HomeClickListener {
    fun onClick(item: HomeItemType)
}