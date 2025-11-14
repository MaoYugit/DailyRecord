const http = require("http");

const server = http.createServer((request, response) => {
  response.end(`<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Document</title>
    <style>
      table,
      td {
        border: 1px solid black;
        border-collapse: collapse;
      }
      td {
        width: 150px;
        height: 50px;
      }
      tr:nth-child(odd) {
        background-color: aqua;
      }
      tr:nth-child(even) {
        background-color: bisque;
      }
    </style>
  </head>
  <body>
    <table>
      <tr>
        <td></td>
        <td></td>
        <td></td>
      </tr>
      <tr>
        <td></td>
        <td></td>
        <td></td>
      </tr>
      <tr>
        <td></td>
        <td></td>
        <td></td>
      </tr>
      <tr>
        <td></td>
        <td></td>
        <td></td>
      </tr>
    </table>
    <script>
      let tds = document.querySelectorAll("td");
      tds.forEach((item) => {
        item.onclick = function () {
          this.style.background = "pink";
        };
      });
    </script>
  </body>
</html>
`);
});

server.listen("9000", () => {
  console.log("服务已启动");
});
