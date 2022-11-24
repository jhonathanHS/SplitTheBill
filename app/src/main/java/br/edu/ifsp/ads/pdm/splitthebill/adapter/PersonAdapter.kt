package br.edu.ifsp.ads.pdm.splitthebill.adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.ifsp.ads.pdm.splitthebill.R
import br.edu.ifsp.ads.pdm.splitthebill.model.Person

class PersonAdapter(
    context: Context,
    private val personList: MutableList<Person>,
    private val Calculate: Boolean = false
) : ArrayAdapter<Person>(context, R.layout.tile_person, personList) {
    private data class TilePersonHolder(val nameTv: TextView, val amountPaidTv: TextView, val amountToPayTv: TextView, val amountReceivableTv: TextView)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val person = personList[position]
        var personTileView = convertView
        if (personTileView == null) {
            // Inflo uma nova célula
            personTileView =
                (context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                    R.layout.tile_person,
                    parent,
                    false
                )

            val tilePersonHolder = TilePersonHolder(
                personTileView.findViewById(R.id.nameTv),
                personTileView.findViewById(R.id.amountPaidTv),
                personTileView.findViewById(R.id.amountToPayTv),
                personTileView.findViewById(R.id.amountReceivableTv),

                )
            personTileView.tag = tilePersonHolder
        }

        with(personTileView?.tag as TilePersonHolder) {
            nameTv.text = person.name
            amountPaidTv.text = String.format("Pagou: R$ %.2f", person.amountPaid)
            if (Calculate) {
                amountToPayTv.text = String.format("Deve Pagar: R$ %.2f", person.amountToPay)
                amountReceivableTv.text = String.format("Deve Receber: R$ %.2f", person.amountReceivable)
            } else {
                amountToPayTv.visibility = View.GONE
                amountReceivableTv.visibility = View.GONE
            }
        }

        return personTileView
    }
}