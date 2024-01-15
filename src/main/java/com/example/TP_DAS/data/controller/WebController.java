package com.example.TP_DAS.data.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebController {

    @GetMapping("/api")
    @ResponseBody
    public String loadHelloPage() {
        // Gere dinamicamente o HTML usando um bloco de texto multilinha
        String codigoHTML = """
                            <!DOCTYPE html>
                            <html lang="en">
                                <head>
                                  <meta charset="UTF-8">
                                  <meta name="viewport" content="width=device-width, initial-scale=1.0">
                                  <title>Compiler App</title>
                                                            
                                  <style>
                                    body {
                                      margin: 0;
                                      padding: 0;
                                    }
                                                            
                                    .container {
                                      display: flex;
                                      justify-content: center;
                                      align-items: center;
                                      height: 100vh;
                                    }
                                                            
                                    .main {
                                      width: 800px;
                                      background-color: #fff;
                                      padding: 20px;
                                      border-radius: 10px;
                                    }
                                                            
                                    .textarea-container {
                                      display: flex;
                                      flex-direction: column;
                                      margin-bottom: 20px;
                                    }
                                                            
                                    textarea {
                                      width: 100%;
                                      min-height: 200px;
                                      padding: 10px;
                                      border: 1px solid #ccc;
                                      border-radius: 5px;
                                      resize: none;
                                    }
                                                            
                                    button {
                                      width: 100%;
                                      background-color: #007bff;
                                      color: #fff;
                                      padding: 10px 20px;
                                      border: none;
                                      border-radius: 5px;
                                      cursor: pointer;
                                    }
                                                            
                                    .resultado {
                                      margin-top: 20px;
                                      padding: 10px;
                                      border: 1px solid #ccc;
                                      border-radius: 5px;
                                      background-color: #f2f2f2;
                                    }
                                                            
                                    .title {
                                      font-size: 24px;
                                      font-weight: bold;
                                      padding: 20px;
                                      border-radius: 5px;
                                    }
                                  </style>
                                </head>
                                <body>
                                  <div class="container">
                                    <div class="main">
                                      <h1 class="title">Compiler App</h1>
                                      <div class="textarea-container">
                                        <textarea id="codigo" placeholder="Insira seu código aqui"></textarea>
                                        <script>
                                          $(document).ready(function () {
                                            $("#codigo").on("input", function () {
                                              this.style.height = "auto";
                                              this.style.height = this.scrollHeight + 10 + "px";
                                            });
                                          });
                                        </script>
                                      </div>
                                                            
                                      <button onclick="compilarCodigo()">Compilar</button>
                                                            
                                      <div class="resultado" id="resultado"></div>
                                    </div>
                                  </div>
                                                            
                                  <script>
                                    function compilarCodigo() {
                                      var codigo = document.getElementById('codigo').value;
                                                            
                                      fetch('/api/compiler', {
                                        method: 'POST',
                                        headers: {
                                          'Content-Type': 'application/json',
                                        },
                                        body: JSON.stringify({ codigo: codigo }),
                                      })
                                      .then(response => response.text())
                                      .then(data => {
                                        document.getElementById('resultado').innerText = data;
                                      })
                                      .catch(error => console.error('Erro:', error));
                                    }
                                  </script>
                                </body>
                            </html>
                            """;

        // Retorne o HTML diretamente como resposta
        return codigoHTML;
    }
    @PostMapping("/api/compiler")
    @ResponseBody
    public String compilarCodigo(@RequestBody String codigo) {
        // Lógica para compilar o código aqui
        // Aqui você receberia o código do frontend, faria a compilação e retornaria o resultado

        // Exemplo simples: apenas retornando o código recebido
        return codigo;
    }
}