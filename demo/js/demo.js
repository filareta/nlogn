var sell = {75.57, 12.57, 21.21, 4.93, 142.29};
var buy = {75.37, 12.25, 21.08, 4.90, 141.61};
var delta = {2.30, -3.32, -8.60, 8.37, 1.01};

function updateRow(row, delta) {
	delta[row] += delta;
	var color, newSell, newBuy;
	if (delta >= 0) {
		color = "green";
	} else {
		color = "red";
	}

	newSell = sell[row] + sell[row] * (delta[row] / 100);
	newBuy = buy[row] + buy[row] * (delta[row] / 100);

	sell[row] = newSell;
	buy[row] = newBuy;

	$(".sharesTable row" + row + " .sell").html(newSell);
	$(".sharesTable row" + row + " .sell").css('color', color);
	$(".sharesTable row" + row + " .buy").html(newBuy);
	$(".sharesTable row" + row + " .buy").css('color', color);
	$(".sharesTable row" + row + " .change").html(delta[row]);
	$(".sharesTable row" + row + " .change").css('color', color);
}