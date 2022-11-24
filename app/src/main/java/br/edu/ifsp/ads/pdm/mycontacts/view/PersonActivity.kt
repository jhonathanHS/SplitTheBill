package br.edu.ifsp.ads.pdm.mycontacts.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.ads.pdm.mycontacts.databinding.ActivityPersonBinding
import br.edu.ifsp.ads.pdm.mycontacts.model.Constant.EXTRA_PERSON
import br.edu.ifsp.ads.pdm.mycontacts.model.Constant.VIEW_PERSON
import br.edu.ifsp.ads.pdm.mycontacts.model.Person

class PersonActivity : AppCompatActivity() {
    private val apb: ActivityPersonBinding by lazy {
        ActivityPersonBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(apb.root)

        val receivedPerson = intent.getParcelableExtra<Person>(EXTRA_PERSON)
        receivedPerson?.let{ _receivedContact ->
            with(apb) {
                with(_receivedContact) {
                    nameEt.setText(name)
                    itemEt.setText(item)
                    amountPaidEt.setText(amountPaid.toString())
                }
            }
        }
        val viewPerson = intent.getBooleanExtra(VIEW_PERSON, false)
        if (viewPerson) {
            apb.nameEt.isEnabled = false
            apb.itemEt.isEnabled = false
            apb.amountPaidEt.isEnabled = false
            apb.saveBt.visibility = View.GONE
        }

        apb.saveBt.setOnClickListener {
            val person = Person(
                id = receivedPerson?.id,
                name = apb.nameEt.text.toString(),
                item = apb.itemEt.text.toString(),
                amountPaid = apb.amountPaidEt.text.toString().toDouble(),
                amountReceivable = null,
                amountToPay = null,
            )
            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_PERSON, person)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}