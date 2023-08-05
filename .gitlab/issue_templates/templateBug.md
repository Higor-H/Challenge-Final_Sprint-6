# Problema Encontrado - Titulo - ![compass.svg](/uploads/217944039619357e305958145c080112/compass.svg)
## RESUMO
Foi testado a regra :
Com base na User Storie foi testado o {login/exemplo/65656565}
O resultado esperado era que {que X se comportasse como X}
Porém o resultado obtido foi de {X se comportou como Y}


| Comportamento esperado  | Comportamento Obtido |
| ------------- | ------------- |
| X se comporta como X  | X se comporta como Y  |
| X não pode mudar o seu valor  |  X não pode mudar o seu valor  |

### Pessoas que testaram
| Pessoas |
| --- |
| Higor Milani |

## O teste foi realizado da seguinte forma:
{ -EXEMPLO-


DELETE http://localhost:3000/usuarios/VDUswLgs6ZQtkNdW (Usuario com carrinho)


Body = none




----------------Response----------------
---------------
400 Bad Request
---------------


```json
{
    "message": "Não é permitido excluir usuário com carrinho cadastrado",
    "idCarrinho": "2kp6nRdIeBF4Teku"
}
```




} -EXEMPLO-


## Informações complementares
{1234567890}
{Para APIs}
| Dado | Valor |
| --- | --- |
| `Status` | 400 Bad Request |
| `Time` | 5 ms |
| `Size` | 608 B |

### Ferramentas

* Postman

### Versão da aplicação

* ServeRest v2.26.11



## Notas e observações:
{a1b2c3d4e5f6g7h8}

## Evidências
(imagem)


![fututre.svg](/uploads/855ce7c4249236fae104ced55d70cd8a/fututre.svg)