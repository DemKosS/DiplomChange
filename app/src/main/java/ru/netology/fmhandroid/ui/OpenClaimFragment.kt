package ru.netology.fmhandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.netology.fmhandroid.R
import ru.netology.fmhandroid.adapter.ClaimCommentListAdapter
import ru.netology.fmhandroid.adapter.OnClaimCommentItemClickListener
import ru.netology.fmhandroid.databinding.FragmentOpenClaimBinding
import ru.netology.fmhandroid.dto.*
import ru.netology.fmhandroid.utils.Events
import ru.netology.fmhandroid.utils.Utils
import ru.netology.fmhandroid.viewmodel.ClaimCardViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@AndroidEntryPoint
class OpenClaimFragment : Fragment() {
    private lateinit var binding: FragmentOpenClaimBinding

    private val claimCardViewModel: ClaimCardViewModel by viewModels()

    private val statusProcessingMenu by lazy {
        PopupMenu(context, binding.statusProcessingImageButton)
    }

    val claimId: Int by lazy {
        val args by navArgs<OpenClaimFragmentArgs>()
        args.argClaim.claim.id!!
    }

    // Временная переменная. После авторизации заменить на залогиненного юзера
    val user = User(
        id = 1,
        login = "User-1",
        password = "abcd",
        firstName = "Дмитрий",
        lastName = "Винокуров",
        middleName = "Владимирович",
        phoneNumber = "+79109008765",
        email = "Vinokurov@mail.ru",
        deleted = false
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        claimCardViewModel.init(claimId)

        lifecycleScope.launchWhenResumed {
            claimCardViewModel.claimStatusChangedEvent.collect {
                claimCardViewModel.dataFullClaim.collect { fullClaim ->
                    binding.statusLabelTextView.text =
                        displayingStatusOfClaim(fullClaim.claim.status!!)

                    statusMenuVisibility(
                        fullClaim.claim.status!!,
                        statusProcessingMenu
                    )

                    binding.titleTextView.text = fullClaim.claim.title

                    binding.executorNameTextView.text =
                        if (fullClaim.executor != null) {
                            Utils.fullUserNameGenerator(
                                fullClaim.executor.lastName.toString(),
                                fullClaim.executor.firstName.toString(),
                                fullClaim.executor.middleName.toString()
                            )
                        } else {
                            getString(R.string.not_assigned)
                        }
                    if (fullClaim.claim.status != Claim.Status.OPEN) {
                        binding.editProcessingImageButton.setImageResource(R.drawable.ic_edit)
                        binding.editProcessingImageButton.isClickable = true

                    } else {
                        binding.editProcessingImageButton.setImageResource(R.drawable.ic_edit_non_clickable)
                        binding.editProcessingImageButton.isClickable = false
                    }
                    when (fullClaim.claim.status) {
                        Claim.Status.OPEN -> {
                            // Заменить на залогиненного юзера и добавить в условие админа !!
                            statusProcessingMenu.menu.findItem(R.id.cancel_list_item).isEnabled =
                                user.id == fullClaim.claim.creatorId
                        }
                        Claim.Status.IN_PROGRESS -> {
                            if (user.id != fullClaim.claim.executorId) {
                                binding.statusProcessingImageButton.setImageResource(R.drawable.ic_status_processing_non_clickable)
                                binding.statusProcessingImageButton.setOnClickListener {
                                    showErrorToast(R.string.no_change_status_rights_executor)
                                }
                            }
                        }
                        Claim.Status.CANCELLED -> {

                        }
                        else -> returnTransition
                    }

                    binding.editProcessingImageButton.apply {
                        isClickable = if (fullClaim.claim.status == Claim.Status.OPEN) {
                            setImageResource(R.drawable.ic_edit)
                            true
                        } else {
                            setImageResource(R.drawable.ic_edit_non_clickable)
                            false
                        }
                    }
                }
            }
        }
        lifecycleScope.launchWhenResumed {
            claimCardViewModel.claimStatusChangeExceptionEvent.collect {
                showErrorToast(R.string.error)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_open_claim, container, false)
    }

    //    TODO("В этом фрагменте после внедрения авторизации требуется изменить хардкод юзера на залогиненного пользователя!!!!")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentOpenClaimBinding.bind(view)

        val args: OpenClaimFragmentArgs by navArgs()
        val claim = args.argClaim


        val adapter = ClaimCommentListAdapter(object : OnClaimCommentItemClickListener {
            override fun onCard(claimComment: ClaimCommentWithCreator) {
                val action = OpenClaimFragmentDirections
                    .actionOpenClaimFragmentToCreateEditClaimCommentFragment(
                        claimComment,
                        claim.claim.id!!
                    )
                findNavController().navigate(action)
            }
        })

        statusProcessingMenu.inflate(R.menu.menu_claim_status_processing)

        statusMenuVisibility(claim.claim.status!!, statusProcessingMenu)
        statusProcessingMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.take_to_work_list_item -> {

                    // Изменить claimExecutor на залогиненного пользователя !!!
                    claimCardViewModel.changeClaimStatus(
                        claimId = claim.claim.id!!,
                        newClaimStatus = Claim.Status.IN_PROGRESS,
                        executorId = user.id,
                        claimComment = Utils.EmptyComment.emptyClaimComment
                    )
                    true
                }

                R.id.cancel_list_item -> {
                    claimCardViewModel.changeClaimStatus(
                        claim.claim.id!!,
                        Claim.Status.CANCELLED,
                        executorId = null,
                        claimComment = Utils.EmptyComment.emptyClaimComment
                    )
                    true
                }

                R.id.throw_off_list_item -> {
                    val dialog = CreateCommentDialogFragment.newInstance(
                        text = "",
                        hint = "Description",
                        isMultiline = true
                    )
                    dialog.onOk = {
                        val text = dialog.editText.text
                        if (text.isNotBlank()) {
                            claimCardViewModel.changeClaimStatus(
                                claim.claim.id!!,
                                Claim.Status.OPEN,
                                executorId = null,
                                claimComment = ClaimComment(
                                    claimId = claim.claim.id,
                                    description = text.toString(),
                                    creatorId = user.id,
                                    createDate = LocalDateTime.now()
                                        .toEpochSecond(
                                            ZoneId.of("Europe/Moscow").rules.getOffset(
                                                Instant.now()
                                            )
                                        )
                                )
                            )
                            dialog.dismiss()
                        } else {
                            showErrorToast(R.string.toast_empty_field)
                        }
                    }
                    dialog.show(parentFragmentManager, "CreateCommentDialog")

                    true
                }

                R.id.executes_list_item -> {
                    val dialog = CreateCommentDialogFragment.newInstance(
                        text = "",
                        hint = "Description",
                        isMultiline = true
                    )
                    dialog.onOk = {
                        val text = dialog.editText.text
                        if (text.isNotBlank()) {
                            claimCardViewModel.changeClaimStatus(
                                claim.claim.id!!,
                                Claim.Status.EXECUTED,
                                executorId = claim.executor?.id,
                                claimComment = ClaimComment(
                                    claimId = claim.claim.id,
                                    description = text.toString(),
                                    creatorId = user.id,
                                    createDate = LocalDateTime.now().toEpochSecond(
                                        ZoneId.of("Europe/Moscow").rules.getOffset(
                                            Instant.now()
                                        )
                                    )
                                )
                            )
                            dialog.dismiss()
                        } else {
                            showErrorToast(R.string.toast_empty_field)
                        }
                    }
                    dialog.show(parentFragmentManager, "CreateCommentDialog")
                    true
                }
                else -> {
                    false
                }
            }
        }

        binding.apply {
            containerCustomAppBarIncludeOnFragmentOpenClaim.customAppBarTitleTextView.visibility =
                View.GONE
            containerCustomAppBarIncludeOnFragmentOpenClaim.customAppBarSubTitleTextView
                .setText(R.string.claim)
            when (claim.claim.status) {
                Claim.Status.OPEN -> {
                    // Заменить на залогиненного юзера и добавить в условие админа !!
                    statusProcessingMenu.menu.findItem(R.id.cancel_list_item).isEnabled =
                        user.id == claim.claim.creatorId
                }
                Claim.Status.IN_PROGRESS -> {
                    if (user.id != claim.claim.executorId) {
                        binding.statusProcessingImageButton.setImageResource(R.drawable.ic_status_processing_non_clickable)
                    }
                }
                Claim.Status.CANCELLED -> {

                }
                Claim.Status.EXECUTED -> {

                }
            }
            titleTextView.text = claim.claim.title
            executorNameTextView.text = if (claim.executor != null) {
                Utils.fullUserNameGenerator(
                    claim.executor.lastName.toString(),
                    claim.executor.firstName.toString(),
                    claim.executor.middleName.toString()
                )
            } else {
                getText(R.string.not_assigned)
            }

            planeDateTextView.text =
                claim.claim.planExecuteDate?.let { Utils.showDateTimeInOne(it) }

            statusLabelTextView.text = displayingStatusOfClaim(claim.claim.status!!)

            descriptionTextView.text = claim.claim.description
            authorNameTextView.text = Utils.fullUserNameGenerator(
                claim.creator.lastName.toString(),
                claim.creator.firstName.toString(),
                claim.creator.middleName.toString()
            )
            createDataTextView.text =
                claim.claim.createDate?.let { Utils.showDateTimeInOne(it) }

            editProcessingImageButton.apply {
                isClickable = if (claim.claim.status == Claim.Status.OPEN) {
                    setImageResource(R.drawable.ic_edit)
                    true
                } else {
                    setImageResource(R.drawable.ic_edit_non_clickable)
                    false
                }
            }

            addCommentImageButton.setOnClickListener {
                val action = OpenClaimFragmentDirections
                    .actionOpenClaimFragmentToCreateEditClaimCommentFragment(
                        argComment = null,
                        argClaimId = claim.claim.id!!
                    )
                findNavController().navigate(action)
            }

            closeImageButton.setOnClickListener {
                findNavController().navigateUp()
            }

            statusProcessingImageButton.setOnClickListener {
                if (claim.claim.status == Claim.Status.IN_PROGRESS && user.id != claim.claim.executorId) {
                    showErrorToast(R.string.no_change_status_rights_executor)
                    return@setOnClickListener
                } else {
                    statusProcessingMenu.show()
                }
            }

            editProcessingImageButton.setOnClickListener {
                if (claim.claim.status != Claim.Status.OPEN) {
                    showErrorToast(R.string.inability_to_edit_claim)
                    return@setOnClickListener
                }
                // Изменить в условии на Id залогиненного юзера!!!
                if (claim.claim.creatorId != user.id) {
                    showErrorToast(R.string.no_editing_rights)
                    return@setOnClickListener
                }
                val action = OpenClaimFragmentDirections
                    .actionOpenClaimFragmentToCreateEditClaimFragment(claim)
                findNavController().navigate(action)
            }
        }

        binding.claimCommentsListRecyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            Events.events.collect {
                claimCardViewModel.claimCommentUpdatedEvent
                claimCardViewModel.dataFullClaim.collect {
                    adapter.submitList(it.comments)
                }
            }
        }

        lifecycleScope.launch {
            claimCardViewModel.dataFullClaim.collect {
                adapter.submitList(it.comments)
            }
        }
    }

    private fun showErrorToast(text: Int) {
        Toast.makeText(
            requireContext(),
            text,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun displayingStatusOfClaim(claimStatus: Claim.Status) =
        when (claimStatus) {
            Claim.Status.CANCELLED -> getString(R.string.cancel)
            Claim.Status.EXECUTED -> getString(R.string.executed)
            Claim.Status.IN_PROGRESS -> getString(R.string.in_progress)
            Claim.Status.OPEN -> getString(R.string.status_open)
        }

    private fun statusMenuVisibility(
        claimStatus: Claim.Status,
        statusProcessingMenu: PopupMenu
    ) {
        when (claimStatus) {
            Claim.Status.OPEN -> {
                statusProcessingMenu.menu.setGroupVisible(R.id.open_menu_group, true)
                statusProcessingMenu.menu.setGroupVisible(
                    R.id.in_progress_menu_group,
                    false
                )
            }
            Claim.Status.IN_PROGRESS -> {
                statusProcessingMenu.menu.setGroupVisible(R.id.open_menu_group, false)
                statusProcessingMenu.menu.setGroupVisible(
                    R.id.in_progress_menu_group,
                    true
                )
            }
            else -> {
                statusProcessingMenu.menu.clear()
                binding.statusProcessingImageButton.apply {
                    setImageResource(R.drawable.ic_status_processing_non_clickable)
                }
            }
        }
    }
}
