package br.edu.ifsp.ads.pdm.splitthebill.view


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.ads.pdm.splitthebill.adapter.PersonAdapter
import br.edu.ifsp.ads.pdm.splitthebill.databinding.ActivitySplitBinding
import br.edu.ifsp.ads.pdm.splitthebill.model.Constant.PERSONS
import br.edu.ifsp.ads.pdm.splitthebill.model.Person

class SplitBillActivity : AppCompatActivity() {
    private val asb: ActivitySplitBinding by lazy {
        ActivitySplitBinding.inflate(layoutInflater)
    }

    private lateinit var personAdapter: PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(asb.root)
        val personList = intent.getParcelableArrayListExtra<Person>(PERSONS)

        var sum = 0.0
        personList?.let { persons ->
            for (person in persons){
                sum += person.amountPaid
            }
            val payPerPerson = (sum / persons.size)

            for (person in persons){
                val payOrReceive = payPerPerson - person.amountPaid
                person.amountToPay = if ((payOrReceive) > 0) payOrReceive else 0.0
                person.amountReceivable = if ((payOrReceive) < 0) -payOrReceive else 0.0
            }
            personAdapter = PersonAdapter(this, personList.toMutableList(), true)
            asb.splitLv.adapter = personAdapter
        }

    }
}