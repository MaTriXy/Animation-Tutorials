package com.smarttoolfactory.tutorial3_1transitions.chapter2_fragment_transitions

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.doOnNextLayout
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.transition.MaterialContainerTransform
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.adapter.SingleViewBinderListAdapter
import com.smarttoolfactory.tutorial3_1transitions.adapter.layoutmanager.SpannedGridLayoutManager
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.ImageModel
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.TravelModel
import com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder.ImageTravelDetailViewBinder
import com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder.ItemBinder

class Fragment2_6ExpandCollapseDetails : Fragment() {

    lateinit var travelModel: TravelModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args = requireArguments()
        travelModel = Fragment2_6ExpandCollapseDetailsArgs.fromBundle(args).travelArgs

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment2_6expand_collapse_detail, container, false)
        bindViews(view)

        prepareSharedElementTransition(view)
        postponeEnterTransition()

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val drawable = AnimatedVectorDrawableCompat.create(
            requireContext(),
            R.drawable.asl_heart_break
        )

        val ivLike = view.findViewById<ImageView>(R.id.ivLike)
        ivLike.setImageDrawable(drawable)

        ivLike.setOnClickListener {
            ivLike.isSelected = !ivLike.isSelected
            val stateSet =
                intArrayOf(android.R.attr.state_checked * if (ivLike.isSelected) 1 else -1)
            ivLike.setImageState(stateSet, true)
        }

        view.doOnNextLayout {
            (it.parent as? ViewGroup)?.doOnPreDraw {
                startPostponedEnterTransition()
            }
        }
    }

    private fun bindViews(view: View) {

        val cardView = view.findViewById<View>(R.id.cardView)
        val ivAvatar = view.findViewById<ImageView>(R.id.ivAvatar)
        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        val tvDate = view.findViewById<TextView>(R.id.tvDate)
        val tvBody = view.findViewById<TextView>(R.id.tvBody)

        cardView.transitionName = "${travelModel.id}"

        val requestOptions = RequestOptions()
        requestOptions
            .placeholder(R.drawable.ic_baseline_account_circle_24)

        Glide
            .with(view.context)
            .setDefaultRequestOptions(requestOptions)
            .load(travelModel.drawableRes)
            .circleCrop()
            .into(ivAvatar)

        tvTitle.text = travelModel.title
        tvDate.text = travelModel.date
        tvBody.text = travelModel.body

        if (!travelModel.images.isNullOrEmpty()) {

            val imageList = travelModel.images!!

            view.findViewById<RecyclerView>(R.id.recyclerView).apply {

                layoutManager = SpannedGridLayoutManager(3, 1f)

                val imageTravelDetailViewBinder = ImageTravelDetailViewBinder()

                val listAdapter =
                    SingleViewBinderListAdapter(imageTravelDetailViewBinder as ItemBinder)

                this.adapter = listAdapter
                setHasFixedSize(true)

                val imageModelList = imageList.map {
                    ImageModel(it)
                }

                listAdapter.submitList(imageModelList)
            }

        }
    }

    private fun prepareSharedElementTransition(view: View) {

        sharedElementEnterTransition = MaterialContainerTransform()
            .apply {
                duration = 500
                // Scope the transition to a view in the hierarchy so we know it will be added under
                // the bottom app bar but over the elevation scale of the exiting HomeFragment.
                drawingViewId = R.id.nav_host_fragment
                scrimColor = Color.TRANSPARENT
//                setAllContainerColors(requireContext().themeColor(R.attr.colorSurface))
            }
    }

}