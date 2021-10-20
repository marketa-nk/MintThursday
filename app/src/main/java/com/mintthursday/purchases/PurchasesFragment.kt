package com.mintthursday.purchases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mintthursday.App
import com.mintthursday.databinding.FragmentPurchasesBinding
import com.mintthursday.models.Purchase

class PurchasesFragment : Fragment() {

    private var _binding: FragmentPurchasesBinding? = null
    private val binding get() = _binding!!

    private val purchaseAdapter: PurchasesAdapter = PurchasesAdapter(
        object : PurchasesAdapter.OnPurchaseClickListener {
            override fun onRemoveClick(purchase: Purchase) {
                deletePurchase(purchase)
            }
        },
        object : PurchasesAdapter.OnItemCheckListener {
            override fun onItemCheck(purchase: Purchase) {
                App.instance.database.purchaseDao().updatePurchase(purchase)
                loadPurchases()
            }

            override fun onItemUncheck(purchase: Purchase) {
                App.instance.database.purchaseDao().updatePurchase(purchase)
                loadPurchases()
            }

        })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPurchasesBinding.inflate(inflater, container, false)

        initRecyclerView(binding)
        loadPurchases()

        return binding.root
    }

    private fun initRecyclerView(binding: FragmentPurchasesBinding) {
        binding.recipeRecyclerViewMain.layoutManager = LinearLayoutManager(context)
        binding.recipeRecyclerViewMain.adapter = purchaseAdapter
    }

    private fun loadPurchases() {
        val purchaseDao = App.instance.database.purchaseDao()
        val recipeDao = App.instance.database.recipeDao()
        val list = mutableListOf<Any>()

        purchaseDao.getIngredientList()
            .groupBy { it.idRecipe }
            .forEach { (idRecipe, purchases) ->
                val name =
                    recipeDao.getRecipeNameById(idRecipe) //todo select recipe names by list of id
                list.add(name)
                list.addAll(purchases)
            }
        purchaseAdapter.submitList(list)
    }

    fun deletePurchase(purchase: Purchase) {
        App.instance.database.purchaseDao().deletePurchase(purchase)
        loadPurchases()
    }
}