<?php
    function displayTrains(array $trains, $formatter) : string {
        $table = "<table>";
        $table .= "<thead>
        <th>Train Number</th>
        <th>Train Type</th>
        <th>Departure</th>
        <th>Arrival</th>
        <th>DepartureTime</th>
        <th>ArrivalTime</th>
        </thead>
        <tbody>";
        foreach ($trains as $train) {
            $table .= $formatter($train);
        }
        $table .= "</tbody></table>";
        return $table;
    }

    function withoutConnecting(array $train) : string {
        return "<tr>
            <td>" . $train["id"] . "</td>
            <td>" . $train["type"] . "</td>
            <td>" . $train["departure"] . "</td>
            <td>" . $train["arrival"] . "</td>
            <td>" . $train["departureTime"] . "</td>
            <td>" . $train["arrivalTime"] . "</td>
        </tr>";
    }

    function withConnecting(array $train) : string {
        $result = "<tr>";
        $result .= "<td>" . $train["id1"] . "</td>";
        $result .= "<td>" . $train["type1"] . "</td>";
        $result .= "<td>" . $train["dept1"] . "</td>";
        $result .= "<td>" . $train["arrv1"] . "</td>";
        $result .= "<td>" . $train["dt1"] . "</td>";
        $result .= "<td>" . $train["at1"] . "</td>";
        $result .= "</tr>";
        $result .= "<tr>";
        $result .= "<td>" . $train["id2"] . "</td>";
        $result .= "<td>" . $train["type2"] . "</td>";
        $result .= "<td>" . $train["dept2"] . "</td>";
        $result .= "<td>" . $train["arrv2"] . "</td>";
        $result .= "<td>" . $train["dt2"] . "</td>";
        $result .= "<td>" . $train["at2"] . "</td>";
        $result .= "</tr>";
        $result .= "<tr><td colspan='6'></td></tr>";
        return $result;
    }