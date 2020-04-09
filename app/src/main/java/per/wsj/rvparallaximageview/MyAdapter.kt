package per.wsj.rvparallaximageview

import android.graphics.Color
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import per.wsj.lib.ScrollWithRvImageView
import per.wsj.lib.controller.LocalImageController
import per.wsj.lib.controller.ResImageController
import per.wsj.rvparallaximageview.controller.GlideImageController
import per.wsj.rvparallaximageview.controller.PicassoImageController
import java.io.File

class MyAdapter(private val recyclerView: RecyclerView) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val pathPrefix = Environment.getExternalStorageDirectory().absolutePath + File.separator

    override fun getItemViewType(position: Int): Int {
        return if (position != 0 && position % 5 == 0) 1 else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_image, parent, false)
            ImageViewViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
            MyViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return 25
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (position) {
            5 -> {      // 资源图
                (holder as ImageViewViewHolder).parallaxImageView.apply {
                    bindRecyclerView(recyclerView)
                    setController(ResImageController(context, R.mipmap.girl))
                }
            }
            10 -> {     // 本地图
                val imagePath = pathPrefix + "a0.jpg";
                (holder as ImageViewViewHolder).parallaxImageView.apply {
                    bindRecyclerView(recyclerView)
                    setController(LocalImageController(imagePath))
                }
            }
            15 -> {     // Glide加载
                val imageUrl = "http://gitstar.com.cn:8000/static/img/1.jpg"
                (holder as ImageViewViewHolder).parallaxImageView.apply {
                    bindRecyclerView(recyclerView)
                    setController(GlideImageController(context, imageUrl))
                }
            }
            20 -> {     // picasso加载
                val imageUrl = "http://gitstar.com.cn:8000/static/img/6.jpg"
                (holder as ImageViewViewHolder).parallaxImageView.apply {
                    bindRecyclerView(recyclerView)
                    setController(PicassoImageController(context, imageUrl))
                }
            }
            else -> {
                (holder as MyViewHolder).tvTitle.text = "position:$position"
                holder.itemView.setBackgroundColor(
                    Color.rgb(
                        (Math.random() * 255).toInt(),
                        (Math.random() * 255).toInt(),
                        (Math.random() * 255).toInt()
                    )
                )
            }
        }
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
    }

    class ImageViewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val parallaxImageView = view.findViewById<ScrollWithRvImageView>(R.id.parallaxImageView)
    }
}