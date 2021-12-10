package de.mycraftnote.code_challenge.ui.task.projects

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import de.mycraftnote.code_challenge.R
import de.mycraftnote.code_challenge.databinding.FragmentProjectListBinding
import de.mycraftnote.code_challenge.ui.formatDate
import de.mycraftnote.code_challenge.ui.hide
import de.mycraftnote.code_challenge.ui.show
import java.util.*


class ProjectListFragment : Fragment() {

    private lateinit var binding: FragmentProjectListBinding
    private lateinit var viewModel: ProjectsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProjectListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, ProjectViewModelFactory()).get(ProjectsViewModel::class.java)
        setupObservers()
        setupLayout()
        viewModel.getProjects()
    }

    private fun setupLayout() {
        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(context)
            tryAgain.setOnClickListener { viewModel.getProjects() }
        }
    }

    private fun setupObservers() {
        viewModel.onShowLoading().observe(viewLifecycleOwner) { loading ->
            if (loading) showLoading() else hideLoading()
        }
        viewModel.onProjectResult().observe(viewLifecycleOwner) {
            onProjectResult(it)
        }
    }

    private fun showLoading() {
        with(binding) {
            loading.show()
            recyclerView.hide()
            errorView.hide()
        }
    }

    private fun hideLoading() {
        with(binding) {
            loading.hide()
            recyclerView.show()
        }
    }

    private fun onProjectResult(result: ProjectResult) {
        result.success?.let { projects ->
            binding.recyclerView.adapter = ProjectAdapter(projects).apply {
                onItemClicked().observe(viewLifecycleOwner) { onItemClicked(it) }
            }
        } ?: showError()
    }

    private fun onItemClicked(lastOpenedOn: Long?) {
        val formattedDate = lastOpenedOn.formatDate(getCurrentLocale())
        Snackbar.make(binding.root, getString(R.string.project_last_opened, formattedDate), Snackbar.LENGTH_LONG).show()
    }

    private fun showError() {
        with(binding) {
            errorView.show()
            recyclerView.hide()
        }
    }

    private fun getCurrentLocale(): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            resources.configuration.locales.get(0)
        } else {
            resources.configuration.locale
        }
    }
}