package co.saputra.alfagifttask.arch.ui.detail

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import co.saputra.alfagifttask.R
import co.saputra.alfagifttask.arch.vm.MainViewModel
import co.saputra.alfagifttask.base.BaseFragment
import co.saputra.alfagifttask.databinding.FragmentDetailBinding
import com.bumptech.glide.Glide
import com.putrash.common.Constant
import com.putrash.common.convertDate
import com.putrash.common.convertTime
import com.putrash.common.handleAvatar
import com.putrash.data.BuildConfig
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : BaseFragment<FragmentDetailBinding, MainViewModel>(
    FragmentDetailBinding::inflate
) {
    override val viewModel: MainViewModel by viewModel()
    private var movieId : Int = Constant.DEFAULT_MOVIE_ID

    override fun initView(view: View, savedInstaceState: Bundle?) {
        movieId = getMovieId()
        binding.apply {
            toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_back)
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            tvReviewMore.setOnClickListener {
                val bundle = bundleOf("movie" to movieId)
                findNavController()
                    .navigate(R.id.action_detailFragment_to_reviewListFragment, bundle)
            }
        }
        viewModel.getMovieDetail(movieId)
        viewModel.getMovieVideo(movieId)
        viewModel.getMovieReviews(movieId)
    }

    override fun observeLiveData() {
        super.observeLiveData()
        viewModel.reviewsMovie.observe(viewLifecycleOwner) { data ->
            binding.apply {
                if (data.isEmpty()) {
                    tvReviewEmpty.visibility = View.VISIBLE
                    tvReviewMore.visibility = View.GONE
                    groupReview.visibility = View.GONE
                } else {
                    tvReviewEmpty.visibility = View.GONE
                    tvReviewMore.visibility = View.VISIBLE
                    groupReview.visibility = View.VISIBLE

                    Glide.with(requireContext())
                        .load(data[0].authorDetails.avatarPath.handleAvatar())
                        .into(ivAvatar)
                    tvReviewName.text = data[0].author
                    tvReviewContent.text = data[0].content
                    tvReview.text = data[0].authorDetails.rating.toString()
                    tvDate.text = data[0].updatedAt?.convertDate(Constant.DATE_FULL_FORMAT, Constant.DATE_DD_MMMM_YYYY_FORMAT)
                }
            }
        }
        viewModel.detailMovie.observe(viewLifecycleOwner) { data ->
            binding.apply {
                Glide.with(requireContext())
                    .load(BuildConfig.IMAGE_URL + data.posterPath)
                    .into(ivPoster)
                tvTitle.text = data.title
                tvDetail.text = getString(
                    R.string.label_subtitle_long,
                    data.releaseDate?.convertDate(Constant.DATE_YYYY_MM_DD_FORMAT, Constant.DATE_YYYY_FORMAT),
                    data.genres?.map { it.name }?.joinToString(", "),
                    data.runtime?.convertTime(),
                )
                tvContent.text = data.overview

            }
        }
        viewModel.videosMovie.observe(viewLifecycleOwner) { data ->
            binding.apply {
                ivPlay.visibility = if (data.isNotEmpty()) View.VISIBLE else View.GONE
                ivPlay.setOnClickListener {
                    try {
                        val appIntent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(getString(R.string.uri_app_youtube, data[0].key))
                        )
                        startActivity(appIntent)
                    } catch (exception: ActivityNotFoundException) {
                        val webIntent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(getString(R.string.uri_web_youtube, data[0].key))
                        )
                        startActivity(webIntent)
                    }
                }
            }
        }
    }

    private fun getMovieId(): Int {
        return arguments?.getInt(Constant.KEY_MOVIE) ?: Constant.DEFAULT_MOVIE_ID
    }
}