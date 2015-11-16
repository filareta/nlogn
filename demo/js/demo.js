var sell = [75.57, 12.57, 21.21, 4.93, 142.29];
var buy = [75.37, 12.25, 21.08, 4.90, 141.61];
var deltas = [2.30, -3.32, -8.60, 8.37, 1.01];

function updateRow(row, delta) {
	delta = parseFloat(delta);
	deltas[row] += delta;
	deltas[row] = parseFloat(deltas[row].toFixed(2));
	var color, newSell, newBuy;
	if (delta >= 0) {
		color = "green";
	} else {
		color = "red";
	}

	newSell = sell[row] + sell[row] * (deltas[row] / 100);
	newBuy = buy[row] + buy[row] * (deltas[row] / 100);

	sell[row] = parseFloat(newSell.toFixed(2));
	buy[row] = parseFloat(newBuy.toFixed(2));

	$(".sharesTable .row" + row + " .sell").html(sell[row]);
	$(".sharesTable .row" + row + " .sell").css('color', color);
	$(".sharesTable .row" + row + " .buy").html(buy[row]);
	$(".sharesTable .row" + row + " .buy").css('color', color);
	$(".sharesTable .row" + row + " .change").html(deltas[row]);
	$(".sharesTable .row" + row + " .change").css('color', color);
}

function setupTimeouts() {
	setInterval(function() {
		var delta = Math.random() * 0.2 - 0.1;
		var row = Math.floor(Math.random() * (5));

		updateRow(row, delta);
	}, 1500);

	setInterval(function() {
		var delta = Math.random() * 0.2 - 0.1;
		var row = Math.floor(Math.random() * (5))	;

		updateRow(row, delta);
	}, 835);
}