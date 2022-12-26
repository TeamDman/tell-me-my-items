import * as vscode from 'vscode';
import { readFileSync, existsSync } from 'fs';
import * as pathlib from "path";

export class Store {
    private items = [];
    public getItems(): string[] {
        return this.items;
    }
    private getContent(path: string): Promise<string> {
        return new Promise((res, rej) => {
            for (const folder of vscode.workspace.workspaceFolders || []) {
                const fullPath = pathlib.resolve(folder.uri.fsPath, path);
                if (existsSync(fullPath)) {
                    res(readFileSync(fullPath, {encoding:"utf-8"}));
                    return;
                }
            }
            if (!existsSync(path)) {
                vscode.window.showErrorMessage("TMMI json not found at " + path);
                rej(new Error("notfound"));
            } else {
                res(readFileSync(path, {encoding:"utf-8"}));
            }
        });
    }
    public async loadItemsFromDisk(path: string) {
        try {
            const content = await this.getContent(path);
            const converted = JSON.parse(content);
            this.items = converted.map((x:any) => {
                let rtn = x.id;
                if (x.data !== undefined) {
                    rtn += " " + x.data;
                }
                return rtn;
            });
        } catch (e) {
            return [];
        }
    }

	public clearItems() {
        this.items = [];        
    }
}