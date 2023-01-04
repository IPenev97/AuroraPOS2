import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.sistechnology.aurorapos2.R
import com.sistechnology.aurorapos2.feature_home.domain.models.receipt.ReceiptItem

@Composable
fun EditReceiptItemDialog(
    receiptItem: ReceiptItem?,
    onDismiss: () -> Unit,
    onDeleteClicked: () -> Unit,
    onSaveClicked: (ReceiptItem) -> Unit
) {


    val orangeColor = colorResource(id = R.color.logo_orange)
    val orangeLightColor = orangeColor.copy(TextFieldDefaults.BackgroundOpacity)
    val blueColor = colorResource(id = R.color.logo_blue)


    var quantity by remember { mutableStateOf(receiptItem?.quantity) }

    LaunchedEffect(key1 = receiptItem) {
        quantity = receiptItem?.quantity
    }



        Dialog(onDismissRequest = onDismiss) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(
                    width = 4.dp, color = colorResource(
                        id = R.color.logo_blue
                    )
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(10.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = receiptItem?.name ?: "",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = orangeColor
                            )
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { quantity = quantity?.minus(1); }) {
                            Icon(
                                modifier = Modifier.size(40.dp),
                                painter = painterResource(id = R.drawable.ic_remove),
                                contentDescription = "Remove Icon",
                                tint = blueColor
                            )
                        }
                        TextField(
                            modifier = Modifier
                                .width(100.dp)
                                .height(50.dp),
                            value = if(quantity==0){""}else{quantity.toString()},
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = orangeColor,
                                backgroundColor = orangeLightColor,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),

                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            onValueChange = {
                                quantity = if (it.isEmpty()) {
                                    0
                                } else {
                                    it.toInt()
                                }
                            })
                        IconButton(onClick = { quantity = quantity?.plus(1) }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_add),
                                modifier = Modifier.size(40.dp),
                                contentDescription = "Add Icon",
                                tint = blueColor
                            )
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Button(
                            onClick = {onDeleteClicked(); onDismiss()}, modifier = Modifier
                                .height(55.dp)
                                .width(105.dp)
                                .layoutId("backButton")
                                .padding(5.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = colorResource(id = R.color.delete_red),
                                disabledContentColor = Color.Transparent,
                                disabledBackgroundColor = Color.Transparent
                            ),
                            shape = CutCornerShape(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Icon",
                                tint = Color.White
                            )
                        }
                        Button(
                            onClick = {
                                onSaveClicked(
                                    ReceiptItem(
                                        name = receiptItem?.name ?: "",
                                        price = receiptItem?.price ?: 0.0,
                                        quantity = quantity ?: 1
                                    )
                                ); onDismiss()
                            }, modifier = Modifier
                                .height(55.dp)
                                .width(105.dp)
                                .padding(5.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = orangeLightColor),
                            shape = CutCornerShape(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Check Icon",
                                tint = orangeColor
                            )
                        }
                    }

                }

            }
        }



}