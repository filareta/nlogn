var sell = [75.57, 12.57, 21.21, 4.93, 22.29];
var buy = [75.37, 12.25, 21.08, 4.90, 21.61];
var deltas = [0.86, -0.32, -0.60, 1.37, 1.01];
var deltasDay = [4.75, -0.46, -1.49, 0.15, 2.23];
var deltasHour = [3.92, -0.28, -1.62, 0.25, 2.84];
var clusterCenters = [];

var companyNames = ["Chevron Corp.", "The Coca Cola Company", "Campbell Soup", "Genuine Parts", "Macys Inc.", "Cerner", "SanDisk Corporation", "Apple Inc.", "Apple Inc.", "Salesforce.com"];

var session = Nirvana.createSession();
session.start();

var channel = session.getChannel("clustering");
channel.on(Nirvana.Observe.DATA, function(evt) {
	clusterCenters = [];
	var clusters = evt.getDictionary().get("clusters");

	for (var i = 0; i < clusters.length; i++) {
		clusterCenters.push([clusters[i].get("pricePerShare"), clusters[i].get("deltaDay"), clusters[i].get("deltaHour"), clusters[i].get("quantity")]);
	}

	var a = 0;
});
channel.subscribe();

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
	$(".sharesTable .row" + row + " .change").html(deltas[row] + "%");
	$(".sharesTable .row" + row + " .change").css('color', color);
}

function setupTimeouts() {
	setInterval(function() {
		var delta = Math.random() * 0.2 - 0.1;
		var row = Math.floor(Math.random() * (5));

		updateRow(row, delta);
	}, 2500);

	setInterval(function() {
		var delta = Math.random() * 0.2 - 0.1;
		var row = Math.floor(Math.random() * (5))	;

		updateRow(row, delta);
	}, 1835);
}

function distance(point1, point2) {
	var distance = 0;
	for (var i = 0; i < point1.length; i++) {
		distance += (point1[i] - point2[i]) * (point1[i] - point2[i]);
	}

	return Math.sqrt(distance);
}

var purchase = function(row) {
	var quantity = parseInt($(".sharesTable .row" + row + " .quantity input").val());
	var purchaseData = [sell[row], deltasDay[row], deltasHour[row], quantity];

	var minDistance = -1, closestCenter = -1;

	for (var i = 0; i < clusterCenters.length; i++) {
		var currentDistance = distance(purchaseData, clusterCenters[i]);

		if (closestCenter == -1 || currentDistance < minDistance) {
			minDistance = currentDistance;
			closestCenter = i;
		}
	}

	var recommendations = [];

	for (var i = 0; i < 5; i++) {
		var recommendation = generateRandomPointGivenACenter(clusterCenters[closestCenter]);
		recommendation[3] = parseInt(recommendation[3]);
		recommendations.push(recommendation);

		var nameIndex = Math.floor(Math.random() * companyNames.length);
		$(".recommendationsTable .row" + i + " .name").html(companyNames[nameIndex]);
		$(".recommendationsTable .row" + i + " .pricePerShare").html(recommendation[0].toFixed(2));
		$(".recommendationsTable .row" + i + " .deltaDay").html(recommendation[1].toFixed(2));
		$(".recommendationsTable .row" + i + " .deltaHour").html(recommendation[2].toFixed(2));
		$(".recommendationsTable .row" + i + " .quantity").html(parseInt(recommendation[3]));

		$("#recommendationsPanel").css('display', 'block');
	}
}

function generateRandomPointGivenACenter(center) {
	var result = [];
	for (var i = 0; i < center.length; i++) {
		var deviationInterval = center[i] / 5; // 20%
		var deviation = Math.random() * deviationInterval - (deviationInterval / 2);

		result.push(center[i] + deviation);
	}

	return result;
}