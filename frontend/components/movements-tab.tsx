'use client'

import { useState } from 'react'
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card'
import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select'
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table'
import { Plus, Search, ArrowUp, ArrowDown, Calendar, X, Filter } from 'lucide-react'
import { Badge } from '@/components/ui/badge'

type Movement = {
  id: string
  product: string
  quantity: number
  type: 'entrada' | 'saída'
  datetime: string
}

export function MovementsTab() {
  const [showCreateModal, setShowCreateModal] = useState(false)
  const [typeFilter, setTypeFilter] = useState<string>('all')
  const [movements, setMovements] = useState<Movement[]>([
    {
      id: '001',
      product: 'Notebook Dell',
      quantity: 10,
      type: 'entrada',
      datetime: '2025-01-15T10:30:00'
    },
    {
      id: '002',
      product: 'Mouse Logitech',
      quantity: 5,
      type: 'saída',
      datetime: '2025-01-15T14:20:00'
    },
    {
      id: '003',
      product: 'Teclado Mecânico',
      quantity: 20,
      type: 'entrada',
      datetime: '2025-01-16T09:15:00'
    },
    {
      id: '004',
      product: 'Notebook Dell',
      quantity: 3,
      type: 'saída',
      datetime: '2025-01-16T16:45:00'
    }
  ])

  const [formData, setFormData] = useState({
    product: '',
    quantity: '',
    type: 'entrada' as 'entrada' | 'saída'
  })

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()
    const newMovement: Movement = {
      id: (movements.length + 1).toString().padStart(3, '0'),
      product: formData.product,
      quantity: parseInt(formData.quantity),
      type: formData.type,
      datetime: new Date().toISOString()
    }
    setMovements([newMovement, ...movements])
    setFormData({ product: '', quantity: '', type: 'entrada' })
    setShowCreateModal(false)
  }

  const formatDateTime = (datetime: string) => {
    const date = new Date(datetime)
    return new Intl.DateTimeFormat('pt-BR', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    }).format(date)
  }

  const filteredMovements = movements.filter(movement => {
    if (typeFilter === 'all') return true
    return movement.type === typeFilter
  })

  return (
    <div className="space-y-6">
      <Card className="bg-card border-border">
        <CardHeader>
          <div className="flex items-center justify-between">
            <div>
              <CardTitle className="text-card-foreground">Histórico de Movimentações</CardTitle>
              <CardDescription>{filteredMovements.length} de {movements.length} movimentações</CardDescription>
            </div>
            <div className="flex items-center gap-3">
              <div className="relative">
                <Search className="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4 text-muted-foreground" />
                <Input placeholder="Buscar..." className="pl-9 w-64" />
              </div>
              <Select value={typeFilter} onValueChange={setTypeFilter}>
                <SelectTrigger className="w-40">
                  <Filter className="w-4 h-4 mr-2" />
                  <SelectValue placeholder="Tipo" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="all">Todos</SelectItem>
                  <SelectItem value="entrada">
                    <div className="flex items-center gap-2">
                      <ArrowDown className="w-4 h-4 text-green-500" />
                      Entrada
                    </div>
                  </SelectItem>
                  <SelectItem value="saída">
                    <div className="flex items-center gap-2">
                      <ArrowUp className="w-4 h-4 text-red-500" />
                      Saída
                    </div>
                  </SelectItem>
                </SelectContent>
              </Select>
              <Button onClick={() => setShowCreateModal(true)}>
                <Plus className="w-4 h-4 mr-2" />
                Nova Movimentação
              </Button>
            </div>
          </div>
        </CardHeader>
        <CardContent>
          <div className="rounded-md border border-border overflow-hidden">
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead className="text-card-foreground">ID</TableHead>
                  <TableHead className="text-card-foreground">Produto</TableHead>
                  <TableHead className="text-card-foreground">Tipo</TableHead>
                  <TableHead className="text-card-foreground">Quantidade</TableHead>
                  <TableHead className="text-card-foreground">Data/Hora</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {filteredMovements.map((movement) => (
                  <TableRow key={movement.id}>
                    <TableCell className="font-mono text-muted-foreground">{movement.id}</TableCell>
                    <TableCell className="font-medium">{movement.product}</TableCell>
                    <TableCell>
                      {movement.type === 'entrada' ? (
                        <Badge className="bg-green-500/10 text-green-500 hover:bg-green-500/20 border-green-500/20">
                          <ArrowDown className="w-3 h-3 mr-1" />
                          Entrada
                        </Badge>
                      ) : (
                        <Badge className="bg-red-500/10 text-red-500 hover:bg-red-500/20 border-red-500/20">
                          <ArrowUp className="w-3 h-3 mr-1" />
                          Saída
                        </Badge>
                      )}
                    </TableCell>
                    <TableCell>
                      <span className="font-semibold">{movement.quantity}</span> unidades
                    </TableCell>
                    <TableCell className="text-muted-foreground">
                      {formatDateTime(movement.datetime)}
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </div>
        </CardContent>
      </Card>

      {showCreateModal && (
        <div className="fixed inset-0 z-50 flex items-center justify-center">
          <div className="absolute inset-0 bg-black/60 backdrop-blur-sm" onClick={() => setShowCreateModal(false)} />
          <Card className="relative w-full max-w-2xl bg-card border-border shadow-2xl m-4">
            <CardHeader>
              <div className="flex items-center justify-between">
                <div>
                  <CardTitle className="text-card-foreground">Nova Movimentação</CardTitle>
                  <CardDescription>Registre entrada ou saída de produtos</CardDescription>
                </div>
                <Button variant="ghost" size="icon" onClick={() => setShowCreateModal(false)}>
                  <X className="w-4 h-4" />
                </Button>
              </div>
            </CardHeader>
            <CardContent>
              <form onSubmit={handleSubmit} className="grid gap-6 md:grid-cols-3">
                <div className="space-y-2">
                  <Label htmlFor="product">Produto</Label>
                  <Select value={formData.product} onValueChange={(value) => setFormData({ ...formData, product: value })}>
                    <SelectTrigger>
                      <SelectValue placeholder="Selecione um produto" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="Notebook Dell">Notebook Dell</SelectItem>
                      <SelectItem value="Mouse Logitech">Mouse Logitech</SelectItem>
                      <SelectItem value="Teclado Mecânico">Teclado Mecânico</SelectItem>
                      <SelectItem value="Monitor LG">Monitor LG</SelectItem>
                    </SelectContent>
                  </Select>
                </div>

                <div className="space-y-2">
                  <Label htmlFor="movementType">Tipo de Movimentação</Label>
                  <Select 
                    value={formData.type} 
                    onValueChange={(value: 'entrada' | 'saída') => setFormData({ ...formData, type: value })}
                  >
                    <SelectTrigger>
                      <SelectValue />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="entrada">
                        <div className="flex items-center gap-2">
                          <ArrowDown className="w-4 h-4 text-green-500" />
                          Entrada
                        </div>
                      </SelectItem>
                      <SelectItem value="saída">
                        <div className="flex items-center gap-2">
                          <ArrowUp className="w-4 h-4 text-red-500" />
                          Saída
                        </div>
                      </SelectItem>
                    </SelectContent>
                  </Select>
                </div>

                <div className="space-y-2">
                  <Label htmlFor="quantity">Quantidade</Label>
                  <Input
                    id="quantity"
                    type="number"
                    placeholder="0"
                    value={formData.quantity}
                    onChange={(e) => setFormData({ ...formData, quantity: e.target.value })}
                    required
                  />
                </div>

                <div className="md:col-span-3">
                  <div className="p-4 rounded-lg bg-muted/50 flex items-start gap-3">
                    <Calendar className="w-5 h-5 text-muted-foreground mt-0.5" />
                    <div className="flex-1">
                      <p className="text-sm font-medium text-muted-foreground">Data/Hora</p>
                      <p className="text-sm text-foreground">{formatDateTime(new Date().toISOString())}</p>
                    </div>
                  </div>
                </div>

                <div className="md:col-span-3">
                  <Button type="submit" className="w-full">
                    <Plus className="w-4 h-4 mr-2" />
                    Registrar Movimentação
                  </Button>
                </div>
              </form>
            </CardContent>
          </Card>
        </div>
      )}
    </div>
  )
}
