'use client'

import { useState } from 'react'
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card'
import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { Switch } from '@/components/ui/switch'
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table'
import { Plus, Search, Edit, Trash2, Tag, X } from 'lucide-react'
import { Badge } from '@/components/ui/badge'
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select'

type Category = {
  id: string
  name: string
  active: boolean
  productCount: number
}

export function CategoriesTab() {
  const [showCreateModal, setShowCreateModal] = useState(false)
  const [showEditModal, setShowEditModal] = useState(false)
  const [editingCategory, setEditingCategory] = useState<Category | null>(null)
  
  const [statusFilter, setStatusFilter] = useState<string>('all')
  
  const [categories, setCategories] = useState<Category[]>([
    { id: '001', name: 'Eletrônicos', active: true, productCount: 15 },
    { id: '002', name: 'Periféricos', active: true, productCount: 28 },
    { id: '003', name: 'Móveis', active: false, productCount: 8 },
    { id: '004', name: 'Acessórios', active: true, productCount: 42 }
  ])

  const [formData, setFormData] = useState({
    name: '',
    active: true
  })

  const [editFormData, setEditFormData] = useState({
    name: '',
    active: true
  })

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()
    const newCategory: Category = {
      id: (categories.length + 1).toString().padStart(3, '0'),
      name: formData.name,
      active: formData.active,
      productCount: 0
    }
    setCategories([...categories, newCategory])
    setFormData({ name: '', active: true })
    setShowCreateModal(false)
  }

  const handleEditCategory = (category: Category) => {
    setEditingCategory(category)
    setEditFormData({
      name: category.name,
      active: category.active
    })
    setShowEditModal(true)
  }

  const handleSaveEdit = (e: React.FormEvent) => {
    e.preventDefault()
    if (editingCategory) {
      setCategories(categories.map(c => 
        c.id === editingCategory.id 
          ? { 
              ...c, 
              name: editFormData.name,
              active: editFormData.active
            }
          : c
      ))
      setShowEditModal(false)
      setEditingCategory(null)
    }
  }

  const filteredCategories = categories.filter(category => {
    if (statusFilter === 'all') return true
    if (statusFilter === 'active') return category.active
    if (statusFilter === 'inactive') return !category.active
    return true
  })

  return (
    <div className="space-y-6">
      <Card className="bg-card border-border">
        <CardHeader>
          <div className="flex items-center justify-between">
            <div>
              <CardTitle className="text-card-foreground">Categorias Cadastradas</CardTitle>
              <CardDescription>{filteredCategories.length} de {categories.length} categorias</CardDescription>
            </div>
            <div className="flex items-center gap-3">
              <div className="relative">
                <Search className="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4 text-muted-foreground" />
                <Input placeholder="Buscar categorias..." className="pl-9 w-64" />
              </div>
              <Select value={statusFilter} onValueChange={setStatusFilter}>
                <SelectTrigger className="w-40">
                  <SelectValue placeholder="Status" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="all">Todas</SelectItem>
                  <SelectItem value="active">Ativas</SelectItem>
                  <SelectItem value="inactive">Inativas</SelectItem>
                </SelectContent>
              </Select>
              <Button onClick={() => setShowCreateModal(true)}>
                <Plus className="w-4 h-4 mr-2" />
                Nova Categoria
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
                  <TableHead className="text-card-foreground">Produtos</TableHead>
                  <TableHead className="text-card-foreground">Status</TableHead>
                  <TableHead className="text-right text-card-foreground">Ações</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {filteredCategories.map((category) => (
                  <TableRow key={category.id}>
                    <TableCell className="font-mono text-muted-foreground">{category.id}</TableCell>
                    <TableCell>
                      <div className="flex items-center gap-2">
                        <div className="w-8 h-8 rounded-lg bg-accent/20 flex items-center justify-center">
                          <Tag className="w-4 h-4 text-accent" />
                        </div>
                        <span className="font-medium">{category.name}</span>
                      </div>
                    </TableCell>
                    <TableCell>
                      <Badge variant="outline">{category.productCount} produtos</Badge>
                    </TableCell>
                    <TableCell>
                      <Badge variant={category.active ? 'default' : 'secondary'}>
                        {category.active ? 'Ativa' : 'Inativa'}
                      </Badge>
                    </TableCell>
                    <TableCell className="text-right">
                      <div className="flex justify-end gap-2">
                        <Button size="icon" variant="ghost" onClick={() => handleEditCategory(category)}>
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
          <Card className="relative w-full max-w-lg bg-card border-border shadow-2xl m-4">
            <CardHeader>
              <div className="flex items-center justify-between">
                <div>
                  <CardTitle className="text-card-foreground">Nova Categoria</CardTitle>
                  <CardDescription>Crie uma nova categoria de produtos</CardDescription>
                </div>
                <Button variant="ghost" size="icon" onClick={() => setShowCreateModal(false)}>
                  <X className="w-4 h-4" />
                </Button>
              </div>
            </CardHeader>
            <CardContent>
              <form onSubmit={handleSubmit} className="grid gap-6">
                <div className="space-y-2">
                  <Label htmlFor="categoryName">Nome da Categoria</Label>
                  <Input
                    id="categoryName"
                    placeholder="Ex: Eletrônicos"
                    value={formData.name}
                    onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                    required
                  />
                </div>

                <div className="flex items-center justify-between p-4 rounded-lg bg-secondary/50">
                  <Label htmlFor="categoryActive" className="text-secondary-foreground">Categoria Ativa</Label>
                  <Switch
                    id="categoryActive"
                    checked={formData.active}
                    onCheckedChange={(checked) => setFormData({ ...formData, active: checked })}
                  />
                </div>

                <Button type="submit" className="w-full">
                  <Plus className="w-4 h-4 mr-2" />
                  Adicionar Categoria
                </Button>
              </form>
            </CardContent>
          </Card>
        </div>
      )}

      {showEditModal && editingCategory && (
        <div className="fixed inset-0 z-50 flex items-center justify-center">
          <div className="absolute inset-0 bg-black/60 backdrop-blur-sm" onClick={() => setShowEditModal(false)} />
          <Card className="relative w-full max-w-lg bg-card border-border shadow-2xl m-4">
            <CardHeader>
              <div className="flex items-center justify-between">
                <div>
                  <CardTitle className="text-card-foreground">Editar Categoria</CardTitle>
                  <CardDescription>ID: {editingCategory.id}</CardDescription>
                </div>
                <Button variant="ghost" size="icon" onClick={() => setShowEditModal(false)}>
                  <X className="w-4 h-4" />
                </Button>
              </div>
            </CardHeader>
            <CardContent>
              <form onSubmit={handleSaveEdit} className="grid gap-6">
                <div className="space-y-2">
                  <Label htmlFor="editName">Nome da Categoria</Label>
                  <Input
                    id="editName"
                    value={editFormData.name}
                    onChange={(e) => setEditFormData({ ...editFormData, name: e.target.value })}
                    required
                  />
                </div>

                <div className="flex items-center justify-between p-4 rounded-lg bg-secondary/50">
                  <Label htmlFor="editActive" className="text-secondary-foreground">Categoria Ativa</Label>
                  <Switch
                    id="editActive"
                    checked={editFormData.active}
                    onCheckedChange={(checked) => setEditFormData({ ...editFormData, active: checked })}
                  />
                </div>

                <Button type="submit" className="w-full">
                  Salvar Alterações
                </Button>
              </form>
            </CardContent>
          </Card>
        </div>
      )}
    </div>
  )
}
