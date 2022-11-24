package br.edu.ifsp.ads.pdm.mycontacts.view

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.AdapterContextMenuInfo
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.ads.pdm.mycontacts.R
import br.edu.ifsp.ads.pdm.mycontacts.adapter.PersonAdapter
import br.edu.ifsp.ads.pdm.mycontacts.databinding.ActivityMainBinding
import br.edu.ifsp.ads.pdm.mycontacts.model.Constant.EXTRA_PERSON
import br.edu.ifsp.ads.pdm.mycontacts.model.Constant.PERSONS
import br.edu.ifsp.ads.pdm.mycontacts.model.Constant.VIEW_PERSON
import br.edu.ifsp.ads.pdm.mycontacts.model.Person

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // Data source
    private val personList: MutableList<Person> = mutableListOf()

    // Adapter
    private lateinit var personAdapter: PersonAdapter

    private lateinit var carl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

       personAdapter = PersonAdapter(this, personList)
        amb.personsLv.adapter = personAdapter

        carl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val person = result.data?.getParcelableExtra<Person>(EXTRA_PERSON)

                person?.let { _person->
                    if (_person.id != null) {
                        val position = personList.indexOfFirst { it.id == _person.id }
                        personList[position] = _person
                    }
                    else {
                        personList.add(_person)
                    }
                    personAdapter.notifyDataSetChanged()
                }
            }
        }

        registerForContextMenu(amb.personsLv)

        amb.personsLv.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val person = personList[position]
                val personIntent = Intent(this@MainActivity, PersonActivity::class.java)
                personIntent.putExtra(EXTRA_PERSON, person)
                personIntent.putExtra(VIEW_PERSON, true)
                startActivity(personIntent)
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.addPersonMi -> {
                carl.launch(Intent(this, PersonActivity::class.java))
                true
            }
            R.id.calculateMi -> {
                startActivity(
                    Intent(
                        this@MainActivity, SplitBillActivity::class.java
                    ).putExtra(
                        PERSONS, ArrayList (personList)
                    )
                )
                true
            }
            else -> { false }
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu_main, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = (item.menuInfo as AdapterView.AdapterContextMenuInfo).position
        return when(item.itemId){
            R.id.removePersonMi -> {
                personList.removeAt(position)
                personAdapter.notifyDataSetChanged()
                true
            }
            R.id.editPersonMi -> {
                val person = personList[position]
                carl.launch(
                    Intent(this, PersonActivity::class.java).putExtra(EXTRA_PERSON, person)
                )
                true
            }
            else -> { false }
        }
    }
}