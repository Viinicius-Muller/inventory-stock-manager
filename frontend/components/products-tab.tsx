'use client'

import { useState } from 'react'
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card'
import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select'
import { Switch } from '@/components/ui/switch'
import { Textarea } from '@/components/ui/textarea'
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table'
import { Plus, Search, Edit, Trash2, X, Filter } from 'lucide-react'
import { Badge } from '@/components/ui/badge'

type Product = {
  id: string
  name: string
  category: string
  description: string
  currentQuantity: number
  minStock: number
  price: number
  active: boolean
}

export function ProductsTab() {
  const [showCreateModal, setShowCreateModal] = useState(false)
  const [showEditModal, setShowEditModal] = useState(false)
  const [editingProduct, setEditingProduct] = useState<Product | null>(null)
  
  const [categoryFilter, setCategoryFilter] = useState<string>('all')
  const [statusFilter, setStatusFilter] = useState<string>('all')
  
  const [products, setProducts] = useState<Product[]>([
    {
      id: '001',
      name: 'Notebook Dell',
      category: 'Eletrônicos',
      description: 'Notebook Dell Inspiron 15',
      currentQuantity: 25,
      minStock: 10,
      price: 3500.00,
      active: true
    },
    {
      id: '002',
      name: 'Mouse Logitech',
      category: 'Periféricos',
      description: 'Mouse wireless Logitech MX Master',
      currentQuantity: 8,
      minStock: 15,
      price: 450.00,
      active: true
    },
    {
      id: '003',
      name: 'Teclado Mecânico',
      category: 'Periféricos',
      description: 'Teclado mecânico RGB',
      currentQuantity: 45,
      minStock: 20,
      price: 650.00,
      active: false
    }
  ])

  const [formData, setFormData] = useState({
    name: '',
    category: '',
    description: '',
    currentQuantity: '',
    minStock: '',
    price: '',
    active: true
  })

  const [editFormData, setEditFormData] = useState({
    name: '',
    category: '',
    price: '',
    active: true
  })

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()
    const newProduct: Product = {
      id: (products.length + 1).toString().padStart(3, '0'),
      name: formData.name,
      category: formData.category,
      description: formData.description,
      currentQuantity: parseInt(formData.currentQuantity),
      minStock: parseInt(formData.minStock),
      price: parseFloat(formData.price),
      active: formData.active
    }
    setProducts([...products, newProduct])
    setFormData({
      name: '',
      category: '',
      description: '',
      currentQuantity: '',
      minStock: '',
      price: '',
      active: true
    })
    setShowCreateModal(false)
  }

  const handleEditProduct = (product: Product) => {
    setEditingProduct(product)
    setEditFormData({
      name: product.name,
      category: product.category,
      price: product.price.toString(),
      active: product.active
    })
    setShowEditModal(true)
  }

  const handleSaveEdit = (e: React.FormEvent) => {
    e.preventDefault()
    if (editingProduct) {
      setProducts(products.map(p => 
        p.id === editingProduct.id 
          ? { 
              ...p, 
              name: editFormData.name,
              category: editFormData.category,
              price: parseFloat(editFormData.price),
              active: editFormData.active
            }
          : p
      ))
      setShowEditModal(false)
      setEditingProduct(null)
    }
  }

  const filteredProducts = products.filter(product => {
    const matchesCategory = categoryFilter === 'all' || product.category === categoryFilter
    const matchesStatus = statusFilter === 'all' || 
      (statusFilter === 'active' && product.active) || 
      (statusFilter === 'inactive' && !product.active)
    return matchesCategory && matchesStatus
  })

  return (
    <div className="space-y-6">
      <Card className="bg-card border-border">
        <CardHeader>
          <div className="flex items-center justify-between">
            <div>
              <CardTitle className="text-card-foreground">Produtos Cadastrados</CardTitle>
              <CardDescription>{filteredProducts.length} de {products.length} produtos</CardDescription>
            </div>
            <div className="flex items-center gap-3">
              <div className="relative">
                <Search className="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4 text-muted-foreground" />
                <Input placeholder="Buscar..." className="pl-9 w-64" />
              </div>
              <Select value={categoryFilter} onValueChange={setCategoryFilter}>
                <SelectTrigger className="w-40">
                  <Filter className="w-4 h-4 mr-2" />
                  <SelectValue placeholder="Categoria" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="all">Todas</SelectItem>
                  <SelectItem value="Eletrônicos">Eletrônicos</SelectItem>
                  <SelectItem value="Periféricos">Periféricos</SelectItem>
                  <SelectItem value="Móveis">Móveis</SelectItem>
                  <SelectItem value="Acessórios">Acessórios</SelectItem>
                </SelectContent>
              </Select>
              <Select value={statusFilter} onValueChange={setStatusFilter}>
                <SelectTrigger className="w-40">
                  <SelectValue placeholder="Status" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="all">Todos</SelectItem>
                  <SelectItem value="active">Ativos</SelectItem>
                  <SelectItem value="inactive">Inativos</SelectItem>
                </SelectContent>
              </Select>
              <Button onClick={() => setShowCreateModal(true)}>
                <Plus className="w-4 h-4 mr-2" />
                Novo Produto
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
                  <TableHead className="text-card-foreground">Nome</TableHead>
                  <TableHead className="text-card-foreground">Categoria</TableHead>
                  <TableHead className="text-card-foreground">Qtd.</TableHead>
                  <TableHead className="text-card-foreground">Preço</TableHead>
                  <TableHead className="text-card-foreground">Status</TableHead>
                  <TableHead className="text-right text-card-foreground">Ações</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {filteredProducts.map((product) => (
                  <TableRow key={product.id}>
                    <TableCell className="font-mono text-muted-foreground">{product.id}</TableCell>
                    <TableCell className="font-medium">{product.name}</TableCell>
                    <TableCell>{product.category}</TableCell>
                    <TableCell>
                      <span className={product.currentQuantity < product.minStock ? 'text-destructive font-semibold' : ''}>
                        {product.currentQuantity}
                      </span>
                    </TableCell>
                    <TableCell>R$ {product.price.toFixed(2)}</TableCell>
                    <TableCell>
                      <Badge variant={product.active ? 'default' : 'secondary'}>
                        {product.active ? 'Ativo' : 'Inativo'}
                      </Badge>
                    </TableCell>
                    <TableCell className="text-right">
                      <div className="flex justify-end gap-2">
                        <Button size="icon" variant="ghost" onClick={() => handleEditProduct(product)}>
                          <Edit className="w-4 h-4" />
                        </Button>
                        <Button size="icon" variant="ghost">
                          <Trash2 className="w-4 h-4" />
                        </Button>
                      </div>
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
          <Card className="relative w-full max-w-3xl max-h-[90vh] overflow-y-auto bg-card border-border shadow-2xl m-4">
            <CardHeader>
              <div className="flex items-center justify-between">
                <div>
                  <CardTitle className="text-card-foreground">Novo Produto</CardTitle>
                  <CardDescription>Adicione um novo produto ao inventário</CardDescription>
                </div>
                <Button variant="ghost" size="icon" onClick={() => setShowCreateModal(false)}>
                  <X className="w-4 h-4" />
                </Button>
              </div>
            </CardHeader>
            <CardContent>
              <form onSubmit={handleSubmit} className="grid gap-6 md:grid-cols-2 lg:grid-cols-3">
                <div className="space-y-2">
                  <Label htmlFor="name">Nome do Produto</Label>
                  <Input
                    id="name"
                    placeholder="Ex: Notebook Dell"
                    value={formData.name}
                    onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                    required
                  />
                </div>

                <div className="space-y-2">
                  <Label htmlFor="category">Categoria</Label>
                  <Select value={formData.category} onValueChange={(value) => setFormData({ ...formData, category: value })}>
                    <SelectTrigger>
                      <SelectValue placeholder="Selecione uma categoria" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="Eletrônicos">Eletrônicos</SelectItem>
                      <SelectItem value="Periféricos">Periféricos</SelectItem>
                      <SelectItem value="Móveis">Móveis</SelectItem>
                      <SelectItem value="Acessórios">Acessórios</SelectItem>
                    </SelectContent>
                  </Select>
                </div>

                <div className="space-y-2">
                  <Label htmlFor="price">Preço (R$)</Label>
                  <Input
                    id="price"
                    type="number"
                    step="0.01"
                    placeholder="0.00"
                    value={formData.price}
                    onChange={(e) => setFormData({ ...formData, price: e.target.value })}
                    required
                  />
                </div>

                <div className="space-y-2 md:col-span-2">
                  <Label htmlFor="description">Descrição</Label>
                  <Textarea
                    id="description"
                    placeholder="Descreva o produto..."
                    value={formData.description}
                    onChange={(e) => setFormData({ ...formData, description: e.target.value })}
                    rows={3}
                  />
                </div>

                <div className="space-y-2">
                  <Label htmlFor="quantity">Quantidade Atual</Label>
                  <Input
                    id="quantity"
                    type="number"
                    placeholder="0"
                    value={formData.currentQuantity}
                    onChange={(e) => setFormData({ ...formData, currentQuantity: e.target.value })}
                    required
                  />
                </div>

                <div className="space-y-2">
                  <Label htmlFor="minStock">Estoque Mínimo</Label>
                  <Input
                    id="minStock"
                    type="number"
                    placeholder="0"
                    value={formData.minStock}
                    onChange={(e) => setFormData({ ...formData, minStock: e.target.value })}
                    required
                  />
                </div>

                <div className="flex items-end">
                  <div className="flex items-center justify-between p-4 rounded-lg bg-secondary/50 w-full">
                    <Label htmlFor="active" className="text-secondary-foreground">Produto Ativo</Label>
                    <Switch
                      id="active"
                      checked={formData.active}
                      onCheckedChange={(checked) => setFormData({ ...formData, active: checked })}
                    />
                  </div>
                </div>

                <div className="md:col-span-2 lg:col-span-3">
                  <Button type="submit" className="w-full">
                    <Plus className="w-4 h-4 mr-2" />
                    Adicionar Produto
                  </Button>
                </div>
              </form>
            </CardContent>
          </Card>
        </div>
      )}

      {showEditModal && editingProduct && (
        <div className="fixed inset-0 z-50 flex items-center justify-center">
          <div className="absolute inset-0 bg-black/60 backdrop-blur-sm" onClick={() => setShowEditModal(false)} />
          <Card className="relative w-full max-w-2xl max-h-[90vh] overflow-y-auto bg-card border-border shadow-2xl m-4">
            <CardHeader>
              <div className="flex items-center justify-between">
                <div>
                  <CardTitle className="text-card-foreground">Editar Produto</CardTitle>
                  <CardDescription>ID: {editingProduct.id}</CardDescription>
                </div>
                <Button variant="ghost" size="icon" onClick={() => setShowEditModal(false)}>
                  <X className="w-4 h-4" />
                </Button>
              </div>
            </CardHeader>
            <CardContent>
              <form onSubmit={handleSaveEdit} className="grid gap-6 md:grid-cols-2">
                <div className="space-y-2">
                  <Label htmlFor="editName">Nome do Produto</Label>
                  <Input
                    id="editName"
                    value={editFormData.name}
                    onChange={(e) => setEditFormData({ ...editFormData, name: e.target.value })}
                    required
                  />
                </div>

                <div className="space-y-2">
                  <Label htmlFor="editCategory">Categoria</Label>
                  <Select value={editFormData.category} onValueChange={(value) => setEditFormData({ ...editFormData, category: value })}>
                    <SelectTrigger>
                      <SelectValue />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="Eletrônicos">Eletrônicos</SelectItem>
                      <SelectItem value="Periféricos">Periféricos</SelectItem>
                      <SelectItem value="Móveis">Móveis</SelectItem>
                      <SelectItem value="Acessórios">Acessórios</SelectItem>
                    </SelectContent>
                  </Select>
                </div>

                <div className="space-y-2">
                  <Label htmlFor="editPrice">Preço (R$)</Label>
                  <Input
                    id="editPrice"
                    type="number"
                    step="0.01"
                    value={editFormData.price}
                    onChange={(e) => setEditFormData({ ...editFormData, price: e.target.value })}
                    required
                  />
                </div>

                <div className="flex items-end">
                  <div className="flex items-center justify-between p-4 rounded-lg bg-secondary/50 w-full">
                    <Label htmlFor="editActive" className="text-secondary-foreground">Produto Ativo</Label>
                    <Switch
                      id="editActive"
                      checked={editFormData.active}
                      onCheckedChange={(checked) => setEditFormData({ ...editFormData, active: checked })}
                    />
                  </div>
                </div>

                <div className="md:col-span-2">
                  <Button type="submit" className="w-full">
                    Salvar Alterações
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
